package me.pgthinker.service;

import org.springframework.ai.chat.prompt.SystemPromptTemplate;

import java.util.List;

/**
 * @Project: me.pgthinker.service
 * @Author: NingNing0111
 * @Github: https://github.com/ningning0111
 * @Date: 2025/3/14 09:43
 * @Description:
 */
public interface VectorSearchService {

    /**
     * 获取TopK相似文本
     * @param topK
     * @param searchText
     * @return
     */
    List<String> getTopSimilarityText(int topK, String searchText);

    /**
     * 获取当前用户TopK可访问的相似文本
     * @param topK
     * @param searchText
     * @return
     */
    List<String> getCurUserAccessTopText(int topK, String searchText);

    List<String> getUserAccessTopText(int topK, String searchText, String knowledgeBaseId);
}
