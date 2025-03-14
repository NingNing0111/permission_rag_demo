package me.pgthinker.service;

import me.pgthinker.model.vo.chat.ChatRequest;
import org.springframework.ai.chat.model.ChatResponse;
import reactor.core.publisher.Flux;

/**
 * @Project: me.pgthinker.service
 * @Author: NingNing0111
 * @Github: https://github.com/ningning0111
 * @Date: 2025/3/14 09:36
 * @Description:
 */
public interface AgentChatService {

    /**
     * 通用对话
     * @param chatRequest
     * @return
     */
    Flux<ChatResponse> generalChat(ChatRequest chatRequest);


    String simpleChatTest(ChatRequest chatRequest);
}
