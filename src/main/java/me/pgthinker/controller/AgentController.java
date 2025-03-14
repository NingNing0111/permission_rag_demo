package me.pgthinker.controller;

import lombok.RequiredArgsConstructor;
import me.pgthinker.model.vo.chat.ChatRequest;
import me.pgthinker.service.AgentChatService;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @Project: me.pgthinker.controller
 * @Author: NingNing0111
 * @Github: https://github.com/ningning0111
 * @Date: 2025/3/14 11:33
 * @Description:
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/agent")
public class AgentController {

    private final AgentChatService agentChatService;;

    @PostMapping(value = "/chat/general", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Message> generalChat(@RequestBody ChatRequest request) {
        return agentChatService.generalChat(request).map(res -> {
            Generation result = res.getResult();
            return result.getOutput();
        }).flatMapSequential(Flux::just);
    }
}
