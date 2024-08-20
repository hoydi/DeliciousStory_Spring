package com.i5.ds.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/").permitAll() // "/" 경로에 대해 인증 없이 접근 가능하도록 설정
//                        .anyRequest().authenticated() // 나머지 요청에 대해서는 인증 필요
//                )
//                .formLogin(Customizer.withDefaults()) // 기본 폼 로그인을 사용하려면 이 설정을 유지
//                .logout(Customizer.withDefaults()); // 기본 로그아웃을 사용하려면 이 설정을 유지
        http
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll() // 모든 요청에 대해 인증 없이 접근 가능하도록 설정
                );
//                .formLogin(Customizer.withDefaults())
//                .logout(Customizer.withDefaults());
        return http.build();
    }
}
