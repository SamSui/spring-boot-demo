package com.xisui.springbootbatch.config.apiversion;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Component
public class PackWebMvcRegistrations implements WebMvcRegistrations {

  @Override
  public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
    return new PackVersionRequestMappingHandlerMapping() ;
  }

}
