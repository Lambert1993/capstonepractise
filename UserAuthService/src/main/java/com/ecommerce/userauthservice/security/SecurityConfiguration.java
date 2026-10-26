package com.ecommerce.userauthservice.security;

import com.ecommerce.userauthservice.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtService jwtService;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){

        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtFilter(jwtService), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth->{
                    auth.requestMatchers("/api/auth/**").permitAll()
                            .anyRequest().authenticated();
                });
            return httpSecurity.build();
    }
}
