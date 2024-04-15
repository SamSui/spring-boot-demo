package com.xisui.springbootbatch.controller;

import com.xisui.springbootbatch.config.apiversion.ApiVersion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  @GetMapping("")
  @ApiVersion("v1")
  public Object v1() {
    return "User v1" ;
  }

  @GetMapping("")
  @ApiVersion("v2")
  public Object v2() {
    return "User v2" ;
  }
}
