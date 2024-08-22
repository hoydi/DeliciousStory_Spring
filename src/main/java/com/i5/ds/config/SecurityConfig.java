package com.i5.ds.config;

import com.i5.ds.User.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/register", "/login").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(logout -> logout
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


//package com.i5.ds.config;
//
//import com.i5.ds.User.CustomUserDetailsService;
//import com.i5.ds.User.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
////import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Autowired
//    private CustomUserDetailsService customUserDetailsService;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/register", "/login").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//////        http
//////                .authorizeHttpRequests(authorize -> authorize
//////                        .requestMatchers("/").permitAll() // "/" 경로에 대해 인증 없이 접근 가능하도록 설정
//////                        .anyRequest().authenticated() // 나머지 요청에 대해서는 인증 필요
//////                )
//////                .formLogin(Customizer.withDefaults()) // 기본 폼 로그인을 사용하려면 이 설정을 유지
//////                .logout(Customizer.withDefaults()); // 기본 로그아웃을 사용하려면 이 설정을 유지
////        http
////                .authorizeHttpRequests(authorize -> authorize
////                        .anyRequest().permitAll() // 모든 요청에 대해 인증 없이 접근 가능하도록 설정
////                );
////
////        http
////                // CSRF 보호 비활성화 (H2 콘솔 접근 시 필요)
////                .csrf(csrf -> csrf.disable())
////                // 세션 관리 정책 설정 (STATELESS로 설정)
////                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
////                // 헤더 설정 (H2 콘솔 iframe 문제 해결)
////                .headers(headers -> headers
////                        .frameOptions(frameOptions -> frameOptions.disable()) // H2 콘솔의 프레임 옵션 문제 해결
////                        .contentSecurityPolicy(csp -> csp.policyDirectives("frame-ancestors 'self'")) // CSP 설정
////                );
//////                .formLogin(Customizer.withDefaults())
//////                .logout(Customizer.withDefaults());
////        return http.build();
////    }
//}
