package com.paymybuddy.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  PasswordEncoder passwordEncoder;

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests()
            .antMatchers("/*").permitAll()
            .antMatchers(HttpMethod.POST, "/users").permitAll()
            .antMatchers(HttpMethod.PUT, "/users*").permitAll()
            .antMatchers(HttpMethod.POST, "/moneyTransaction/*").permitAll()
            .antMatchers(HttpMethod.PUT, "/users/pwd*").permitAll()
            .antMatchers(HttpMethod.DELETE, "/users*").permitAll()
            .antMatchers(HttpMethod.GET, "/users/*").permitAll()
            .antMatchers(HttpMethod.GET, "/moneyTransaction*").permitAll()

            .anyRequest().authenticated();


    //hasROle("admin")
  }

  @Override
  protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
            .withUser("FrontEnd").password(passwordEncoder.encode("7LS`kFLzk})u^wr")).roles(
            "ADMIN");


  }
}
