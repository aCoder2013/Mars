package com.song.core.init;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by song on 16/6/29.
 */
@ConfigurationProperties(prefix = "com.mars")
public class MarsProperties {

  private String basePackage;

  public String getBasePackage() {
    return basePackage;
  }

  public void setBasePackage(String basePackage) {
    this.basePackage = basePackage;
  }
}
