package org.example.textChatApp.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Отключить CSRF для упрощения тестирования
                .authorizeHttpRequests()
                .anyRequest().permitAll() // Остальные запросы требуют авторизации
                .and()
                .httpBasic().disable(); // Отключить базовую авторизацию
        return http.build();
    }
}
