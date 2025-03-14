package me.pgthinker.model.vo.chat;

import lombok.Data;

import java.util.List;

/**
 * @Project: me.pgthinker.model.vo
 * @Author: NingNing0111
 * @Github: https://github.com/ningning0111
 * @Date: 2025/3/14 09:38
 * @Description:
 */
@Data
public class ChatRequest {
    private Integer maxLength;
    private String question;
    private List<SimpleMessage> historySimpleMessage;
    // 知识库id
    private List<String> knowledgeBaseIds;
    // 文档id
    private List<Long> documentIds;
}
