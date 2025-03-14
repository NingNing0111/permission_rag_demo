package me.pgthinker.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Project: me.pgthinker.utils
 * @Author: NingNing0111
 * @Github: https://github.com/ningning0111
 * @Date: 2025/3/13 23:37
 * @Description:
 */
public class DocumentUtil {

    // 简单处理 去掉换行 空格
    public static List<Document> simpleHandler(List<Document> documentList) {
        return documentList.stream().map(document -> {
            String text = document.getText();
            String lastText = "";
            if(text != null) {
                //只保留字母、数字和汉字
                lastText = text.replaceAll("[^a-zA-Z0-9\\u4e00-\\u9fa5]", "");
            }
            return new Document(lastText,document.getMetadata());
        }).collect(Collectors.toList());
    }

    // TODO：深层次处理 分词 然后过滤掉停止词
}
