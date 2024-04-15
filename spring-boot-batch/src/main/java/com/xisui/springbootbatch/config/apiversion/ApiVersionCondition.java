package com.xisui.springbootbatch.config.apiversion;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

public class ApiVersionCondition implements RequestCondition<ApiVersionCondition> {
  private final String apiVersion;
  public ApiVersionCondition(String apiVersion) {
    this.apiVersion = apiVersion;
  }
  @Override
  public ApiVersionCondition combine(ApiVersionCondition other) {
    // 这里可以根据需要实现组合逻辑，例如只保留最新的版本
    return this;
  }
  @Override
  public ApiVersionCondition getMatchingCondition(HttpServletRequest request) {
    String requestedVersion = request.getHeader("X-API-Version");
    if (apiVersion.equals(requestedVersion)) {
      return this ;
    }
    return null;
  }
  @Override
  public int compareTo(ApiVersionCondition other, HttpServletRequest request) {
    return this.apiVersion.compareTo(other.apiVersion);
  }
}
