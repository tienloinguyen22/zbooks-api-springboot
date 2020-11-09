package com.neoflies.zbooks.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfigs {
  private String secretKey;

  private Integer tokenValidation;

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  public Integer getTokenValidation() {
    return tokenValidation;
  }

  public void setTokenValidation(Integer tokenValidation) {
    this.tokenValidation = tokenValidation;
  }
}
