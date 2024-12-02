package org.example.textChatApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Отключить CSRF для упрощения тестирования
                .authorizeHttpRequests()
                .requestMatchers("/api/auth/**", "/api/users/**", "/api/chats/**").permitAll() // Разрешить доступ к маршрутам
                .anyRequest().authenticated() // Остальные запросы требуют авторизации
                .and()
                .httpBasic().disable(); // Отключить базовую авторизацию
        return http.build();
    }
}
