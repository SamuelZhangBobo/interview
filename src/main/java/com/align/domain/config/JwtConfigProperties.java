package com.align.domain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "authenticator.jwt")
public class JwtConfigProperties {

    /**
     * default 30 minutes
     */
    private long accessTokenExpireTime = 30L;

    /**
     * default 360 minutes
     */
    private long refreshTokenExpireTime = 360L;

    private String accessSecret = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDR7";

    private String refreshSecret = "9dr6vsnXbZ/JjR3P07fu5m+Ysj0cArxlVVcJxNeDTMPswoICjN4Ho6RhXh";

}
