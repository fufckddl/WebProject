package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //이 클래스는 Spring 설정해주는 코드이다. 라는 뜻임
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();
    } //비밀번호를 암호화 시켜 저장

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{ //권한 검사(매 요청마다 권한이 있는 검사하는 설정)
        http
                //URL에 따라서 접근권한을 설정함
                .authorizeHttpRequests(auth->auth
                        //누구나 접근가능(CSS,JS 정적자원 관리하는데 이걸 사용 가능하도록)
                        .requestMatchers("/", "/user/login", "/user/signup", "/css/**", "/js/**").permitAll() //permitALL()전부 허용
                        //누구나 접근불가
                        .requestMatchers("/free/create", "/free/modify/**", "/free/delete/**", "/comment/write").authenticated() //로그인한 사람만 접근 허용
                        .anyRequest().permitAll() //그 외(위에 3개 제외)는 전부 허용한다는 뜻
                )
                //.csrf(csrf-> csrf.disable()) => 보안과 관련된 것, 내가 아닌 다른 사람이 내 이름으로 요청을 보내는 것을 차단(disable)함
                //로그인 설정
                .formLogin(login -> login
                        //로그인 페이지는 /user/login 이라는 뜻
                        .loginPage("/user/login")
                        .loginProcessingUrl("/login")
                        //로그인 성공한 후 redirect 페이지
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                //로그아웃 설정
                .logout(logout -> logout
                        //로그아웃 url설정
                        .logoutUrl("/logout")
                        //로그아웃 성공시 redirect
                        .logoutSuccessUrl("/")
                        //세션을 무효화
                        .invalidateHttpSession(true)
                        //인증되어있는 정보도 지운다는 뜻
                        .clearAuthentication(true)
                );
        return http.build();
    }
}
