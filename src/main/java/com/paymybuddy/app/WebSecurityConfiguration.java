package com.paymybuddy.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests()
            .antMatchers("/*").permitAll()
            .antMatchers(HttpMethod.POST,"/users").permitAll()
            .antMatchers(HttpMethod.PUT,"/users*").permitAll()
            .antMatchers(HttpMethod.POST,"/moneyTransaction/*").permitAll()
            .antMatchers(HttpMethod.PUT,"/users/pwd*").permitAll()
            .antMatchers(HttpMethod.DELETE,"/users*").permitAll()
            .antMatchers(HttpMethod.GET,"/users/*").permitAll()
            .antMatchers(HttpMethod.GET,"/moneyTransaction*").permitAll()

            .anyRequest().authenticated();
  }
}
