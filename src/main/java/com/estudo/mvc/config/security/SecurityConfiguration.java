package com.estudo.mvc.config.security;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.estudo.mvc.service.UserService;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

  @Autowired
  private TokenService tokenService;

  @Autowired
  private UserService userService;

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests()
      .antMatchers(HttpMethod.POST, "/auth").permitAll()
      .antMatchers(HttpMethod.POST, "/user").permitAll()
      .anyRequest().authenticated().and().csrf().disable().sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
      .addFilterBefore(new AuthTokenFilter(tokenService, userService), UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
  
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
