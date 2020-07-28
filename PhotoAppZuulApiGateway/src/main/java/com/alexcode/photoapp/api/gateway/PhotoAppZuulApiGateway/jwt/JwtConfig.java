package com.alexcode.photoapp.api.gateway.PhotoAppZuulApiGateway.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    private String secretKey;
    private String authorizationHeader;
    private String tokenPrefix;
    private Integer tokenExpirationDays;
}