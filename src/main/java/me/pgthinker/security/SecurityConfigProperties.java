package me.pgthinker.security;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Project: me.pgthinker.security
 * @Author: NingNing0111
 * @Github: https://github.com/ningning0111
 * @Date: 2025/3/13 21:04
 * @Description:
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityConfigProperties {
    private List<String> allowList;
    private String secretKey;
    private long expiration;
}
