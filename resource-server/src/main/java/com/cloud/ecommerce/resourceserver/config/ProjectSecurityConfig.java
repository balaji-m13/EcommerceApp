package com.cloud.ecommerce.resourceserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    public SecurityFilterChain configureSecurity(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize) ->
                authorize.requestMatchers("/api/shop/products", "/api/shop/brands", "/api/shop/products/**").permitAll());
//                        .requestMatchers("/api/shop/categories").authenticated())
//                .httpBasic(Customizer.withDefaults()).formLogin(Customizer.withDefaults());
        return http.build();
    }
}
