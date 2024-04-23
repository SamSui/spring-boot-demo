package com.xisui.springbootbatch.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SecurityConfig {

  private final AdminServerProperties adminServer;

  private final SecurityProperties security;

  public SecurityConfig(AdminServerProperties adminServer, SecurityProperties security) {
    this.adminServer = adminServer;
    this.security = security;
  }

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable) ;
    SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
    successHandler.setTargetUrlParameter("redirectTo");
    successHandler.setDefaultTargetUrl(this.adminServer.path("/"));

    http.authorizeHttpRequests(registry -> {
      registry.requestMatchers(new AntPathRequestMatcher(this.adminServer.path("/assets/**"))).permitAll() ;
      registry.requestMatchers(new AntPathRequestMatcher(this.adminServer.path("/actuator/info"))).permitAll() ;
      registry.requestMatchers(new AntPathRequestMatcher(adminServer.path("/actuator/health"))).permitAll() ;
      registry.requestMatchers(new AntPathRequestMatcher(this.adminServer.path("/login"))).permitAll() ;
      registry.anyRequest().authenticated() ;
    }) ;
    http.formLogin(configurer -> {
      configurer.loginPage(this.adminServer.path("/login")).successHandler(successHandler) ;
    }) ;
    http.logout(configurer -> {
      configurer.logoutUrl(this.adminServer.path("/logout")) ;
    }) ;
    // 该配置是为client端注册时使用（client端注册调用/instances时通过basic方式进行验证）
    http.httpBasic(Customizer.withDefaults()) ;

    return http.build();
  }
  @Bean
  InMemoryUserDetailsManager userDetailsService() {
    org.springframework.boot.autoconfigure.security.SecurityProperties.User u = this.security.getUser();
    UserDetails user = User.withDefaultPasswordEncoder().username(u.getName()).password(u.getPassword()).roles(u.getRoles().toArray(new String[0])).build();
    return new InMemoryUserDetailsManager(user) ;
  }
  @Bean
  PasswordEncoder passwordEncoder() {
    //String idForEncode = "bcrypt";
    //Map<String,PasswordEncoder> encoders = new HashMap<>();
    //encoders. put(idForEncode, new BCryptPasswordEncoder());
    //encoders. put("noop", NoOpPasswordEncoder. getInstance());
    //
    //PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(idForEncode, encoders);
    return NoOpPasswordEncoder.getInstance();
    //return passwordEncoder;
  }
}
