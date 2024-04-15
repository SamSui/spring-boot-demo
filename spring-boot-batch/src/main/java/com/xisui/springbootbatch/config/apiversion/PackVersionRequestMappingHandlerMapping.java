package com.xisui.springbootbatch.config.apiversion;

import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

public class PackVersionRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

  @Override
  protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
    RequestMappingInfo mappingInfo = super.getMappingForMethod(method, handlerType);
    if (mappingInfo != null) {
      // 获取请求方法上的@ApiVersion注解信息
      ApiVersion apiVersionAnnotation = method.getAnnotation(ApiVersion.class) ;
      if (apiVersionAnnotation != null) {
        String apiVersion = apiVersionAnnotation.value() ;
        ApiVersionCondition apiVersionCondition = new ApiVersionCondition(apiVersion) ;
        // 添加自定义注解条件
        mappingInfo = mappingInfo.addCustomCondition(apiVersionCondition) ;
      }
    }
    return mappingInfo;
  }

  @Override
  protected boolean isHandler(Class<?> beanType) {
    return super.isHandler(beanType);
  }
}
