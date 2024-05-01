package com.app.businessBridge.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class APISecurityConfig {

    @Bean
    SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")
                .authorizeRequests(
                        authorizeRequests -> authorizeRequests
                                //.requestMatchers("API URI").permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/chats/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/rebates/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/workingstates/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/statistics/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/educations/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/schedules/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/schedules")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/confirm-statuses/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/confirm-statuses")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/confirm-form-types/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/confirm-form-types")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/confirms/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/confirms")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/members")).permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/*/members/login").permitAll() // 로그인
                                .requestMatchers(HttpMethod.POST, "/api/*/members/logout").permitAll() // 로그아웃
                                .requestMatchers(new AntPathRequestMatcher("/api/*/grades")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/grades/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/departments")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/departments/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/mailboxes")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/mailboxes/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/mails")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/mails/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/articles/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/answers/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/clubs/**")).permitAll()
                                .anyRequest().authenticated()
                )
                .csrf(
                        csrf -> csrf
                                .disable()
                ); // csrf 토큰 끄기
        return http.build();
    }
}