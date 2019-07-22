package com.cristian.simplestore.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// @Profile("test")
@Configuration
@EnableWebSecurity
@SuppressWarnings("unused")
public class DisableSecurity extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
      // @formatter:off
      http
        .cors()
            .and()
        .csrf()
            .disable()
        .formLogin()
            .disable()
        .httpBasic()
            .disable()
        .authorizeRequests()
            .antMatchers("/**").permitAll().anyRequest().permitAll();
      // @formatter:on
    }
}
