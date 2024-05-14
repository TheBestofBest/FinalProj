package com.app.businessBridge.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class APISecurityConfig {
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")
                .authorizeRequests(
                        authorizeRequests -> authorizeRequests
                                //.requestMatchers("API URI").permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/chats/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/meetings/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/logs/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/rebates/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/workingstates/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/statistics/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/educations/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/confirm-statuses/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/confirm-statuses")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/confirm-form-types/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/confirm-form-types")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/confirms/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/confirms")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/members")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/*/members/login")).permitAll() // 로그인
                                .requestMatchers(new AntPathRequestMatcher("/api/*/members/logout")).permitAll() // 로그아웃
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
                                .requestMatchers(new AntPathRequestMatcher("/api/*/members/search")).permitAll()
                                .anyRequest().authenticated()
                )
                .csrf(
                        csrf -> csrf
                                .disable()
                ) // csrf 토큰 끄기
                .httpBasic(
                        httpBasic -> httpBasic.disable()
                ) // httpBasic 로그인 방식 끄기
                .formLogin(
                        formLogin -> formLogin.disable()
                ) // 폼 로그인 방식 끄기
                .sessionManagement(
                        sessionManagement -> sessionManagement.sessionCreationPolicy(STATELESS)
                ) // 세션 끄기
                .addFilterBefore(
                        jwtAuthorizationFilter, //엑세스 토큰을 이용한 로그인 처리
                        UsernamePasswordAuthenticationFilter.class
                );
        return http.build();
    }
}