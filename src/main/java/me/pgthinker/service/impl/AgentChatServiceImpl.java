package me.pgthinker.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.pgthinker.common.ErrorCode;
import me.pgthinker.exception.BusinessException;
import me.pgthinker.model.vo.chat.ChatRequest;
import me.pgthinker.model.vo.chat.SimpleMessage;
import me.pgthinker.service.AgentChatService;
import me.pgthinker.service.VectorSearchService;
import org.springframework.ai.chat.messages.*;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.StreamingChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Project: me.pgthinker.service.impl
 * @Author: NingNing0111
 * @Github: https://github.com/ningning0111
 * @Date: 2025/3/14 09:42
 * @Description:
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class AgentChatServiceImpl implements AgentChatService {

    private final VectorSearchService vectorSearchService;
    private final static Integer TOP_K = 5;
    private final StreamingChatModel chatModel;
    @Value("classpath:prompt/RAG.st")
    private Resource systemRAGResource;

    @Override
    public Flux<ChatResponse> generalChat(ChatRequest chatRequest) {
        List<SimpleMessage> historySimpleMessage = chatRequest.getHistorySimpleMessage();
        String question = chatRequest.getQuestion();
        Integer maxLength = chatRequest.getMaxLength();
        List<Message> historyMessage = new ArrayList<>();
        if(!historySimpleMessage.isEmpty()) {
            historyMessage.addAll(transferAiMessage(historySimpleMessage));
        }
        Message systemMessage;
        if(!historyMessage.isEmpty() && historyMessage.get(0).getMessageType().equals(MessageType.SYSTEM)) {
            systemMessage = buildSystemMessage(question, historyMessage.get(0).getText());
            historyMessage.remove(0); // 先移除出去
        }else{
            systemMessage = buildSystemMessage(question, "");
        }
        List<Message> chatMessageList = new ArrayList<>();
        chatMessageList.add(systemMessage);
        chatMessageList.addAll(historyMessage);
        List<Message> handledChatMessageList = checkMessageLength(chatMessageList, maxLength);
        return chatModel.stream(new Prompt(handledChatMessageList));
    }

    private List<Message> transferAiMessage(List<SimpleMessage> messages) {
        List<Message> aiMessage = new ArrayList<>();
        for(SimpleMessage message: messages){
            String role = message.getRole();
            String content = message.getContent();
            MessageType aiMessageType = MessageType.fromValue(role);
            switch (aiMessageType){
                case USER -> aiMessage.add(new UserMessage(content));
                case SYSTEM -> aiMessage.add(new SystemMessage(content));
                case ASSISTANT -> aiMessage.add(new AssistantMessage(content));
                default -> throw new BusinessException(ErrorCode.PARAMS_ERROR,"对话列表存在未知类别:" + role);
            }
        }
        return aiMessage;
    }

    private Message buildSystemMessage(String question, String systemPrompt) {
        List<String> curUserAccessTopText = vectorSearchService.getCurUserAccessTopText(TOP_K, question);
        log.info("====>context:{}", curUserAccessTopText);
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemRAGResource);
        return systemPromptTemplate.createMessage(Map.of("question", question, "context", curUserAccessTopText,"userSystemPrompt",systemPrompt));
    }

    // 保证消息长度在配置的长度范围内 截取时 若第一个Message是System 则需要保留
    private List<Message> checkMessageLength(List<Message> messages,  int maxMessageLength){
        if(messages.isEmpty() || messages.size() < maxMessageLength){
            return messages;
        }
        Message systemMessage = null;
        if(messages.get(0).getMessageType() == MessageType.SYSTEM){
            systemMessage = messages.get(0);
        }
        int currMessageSize = messages.size();
        if(currMessageSize > maxMessageLength){
            messages = messages.subList(currMessageSize-maxMessageLength,currMessageSize);
        }
        if(systemMessage != null){
            messages.add(0, systemMessage);
        }
        return messages;
    }

}
