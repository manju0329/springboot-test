package com.test.spring.config.auth;

import com.test.spring.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .headers().frameOptions().disable() // h2-console 화면 사용
                .and()
                    .authorizeRequests()
                    // url별 권한 관리 시작 ->  /api/v1/** url은 user 권한 필요
                    .antMatchers("/", "/css/**", "images/**", "/js/**", "/h2-console/**").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                    .anyRequest().authenticated() // 설정된 값들 이외 나머지 url -> authenticated() : 로그인한 사용자만 허용
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                        .userInfoEndpoint()
                        //로그인 성공 이후 사용자 정보 가져올 때 설정
                            .userService(customOAuth2UserService); // 소셜 로그인 성공 시 진행되는 인터페이스

    }
}
