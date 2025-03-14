package me.pgthinker.config;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @Project: me.pgthinker.config
 * @Author: NingNing0111
 * @Github: https://github.com/ningning0111
 * @Date: 2025/3/13 23:13
 * @Description:
 */
@Configuration
@RequiredArgsConstructor
public class AppConfig {


    @Bean
    public TokenTextSplitter tokenTextSplitter() {
        return new TokenTextSplitter();
    }

}
