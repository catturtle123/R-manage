package com.rmanage.rmanage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(c->c.disable());
        http.authorizeRequests().requestMatchers("/test").permitAll() // /test에는 인증 없이 접근 가능
                .requestMatchers("/every/workEmployees/**").permitAll();
        http.cors(c->c.disable());

        return http.build();
    }

}
