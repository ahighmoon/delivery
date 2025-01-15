package com.laioffer.delivery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // 禁用 CSRF
            .authorizeHttpRequests(auth -> auth
                .requestMatchers( "/swagger-ui/index.html", "/api-docs/**").permitAll() // Swagger 和 API 文档允许匿名访问
                .anyRequest().authenticated() // 其他请求需要认证
            )
            .httpBasic(Customizer.withDefaults()) // 默认启用 HTTP Basic 认证（可选）
            .formLogin(Customizer.withDefaults()); // 默认启用表单登录（可选）

        return http.build();
    }
}
