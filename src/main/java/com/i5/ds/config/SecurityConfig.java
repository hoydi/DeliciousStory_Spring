package com.i5.ds.config;

import com.i5.ds.User.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;

/**
 * Spring Security 설정을 담당하는 클래스입니다.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // AuthenticationConfiguration을 주입받아 AuthenticationManager를 구성합니다.
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    /**
     * 비밀번호를 암호화하기 위한 PasswordEncoder 빈을 정의합니다.
     * BCryptPasswordEncoder는 강력한 암호화 알고리즘을 사용하여 비밀번호를 암호화합니다.
     *
     * @return BCryptPasswordEncoder 빈
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationManager 빈을 정의합니다.
     * AuthenticationManager는 인증 요청을 처리하는데 사용됩니다.
     * AuthenticationConfiguration을 통해 AuthenticationManager를 구성합니다.
     *
     * @return AuthenticationManager 빈
     * @throws Exception 예외가 발생할 수 있음
     */
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Spring Security의 보안 필터 체인을 설정합니다.
     * HTTP 요청에 대한 권한 부여 및 인증을 구성합니다.
     *
     * @param http HttpSecurity 객체
     * @return SecurityFilterChain 빈
     * @throws Exception 예외가 발생할 수 있음
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 모든 요청을 허용합니다. (추후 필요에 따라 권한 설정을 추가할 수 있습니다.)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .anyRequest().permitAll()
                )
                .formLogin(formLogin -> formLogin
                        // 로그인 페이지의 URL을 설정합니다.
                        .loginPage("/login")
                        // 로그인 처리 요청의 URL을 설정합니다.
                        .loginProcessingUrl("/login")
                        // 로그인 성공 시 이동할 기본 URL을 설정합니다.
                        .defaultSuccessUrl("/", true)
                        // 로그인 실패 시 이동할 URL을 설정합니다.
                        .failureUrl("/login?error=true")
                        // 모든 사용자에게 로그인 페이지 접근을 허용합니다.
                        .permitAll()
                )
                .logout(logout -> logout
                        // 로그아웃 요청의 URL을 설정합니다.
                        .logoutUrl("/logout")
                        // 로그아웃 시 모든 사용자에게 접근을 허용합니다.
                        .permitAll()
                )
                // CSRF 보호를 비활성화합니다. (CSRF 공격에 대한 보호가 필요할 경우 활성화할 수 있습니다.)
                .csrf(csrf -> csrf
                        .disable());
        // 보안 필터 체인 객체를 반환합니다.
        return http.build();
    }
}
