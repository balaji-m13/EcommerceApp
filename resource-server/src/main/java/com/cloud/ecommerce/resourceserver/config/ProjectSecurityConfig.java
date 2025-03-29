package com.cloud.ecommerce.resourceserver.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class ProjectSecurityConfig {


    @Bean
    public SecurityFilterChain configureSecurity(HttpSecurity http) throws Exception {

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();

        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                        // config.setAllowedOrigins(Collections.singletonList("http://localhost:8070"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setExposedHeaders(Arrays.asList("Authorization"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                }));


        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers("/api/deliverymethods",
                                        "/api/shop/categories","/api/shop/brands","/api/shop/products",
                                        "/api/shop/products/**","/api/basket","/api/basket/**",
                                        "/api/webhook", "/api/webhook/**", "/api/webhook/hello")

                                .permitAll()
                                .requestMatchers("/api/orders", "/api/orders/**").authenticated()
                                // .requestMatchers("/api/orders").hasRole("ADMIN"))
                                .requestMatchers("/api/order", "/api/order/**",  "/api/payments/","/api/payments/**").authenticated())
                .oauth2ResourceServer(oauth2->oauth2.jwt(jwtCustomizer->
                        jwtCustomizer.jwtAuthenticationConverter(jwtAuthenticationConverter)));



        //.requestMatchers("/api/shop/categories").authenticated());
        // .httpBasic(Customizer.withDefaults())
        //.formLogin(Customizer.withDefaults());


        return http.build();


    }

}
