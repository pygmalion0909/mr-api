package com.mr.mr_api.common.config;

import com.mr.mr_api.common.handler.MrExceptionHandler;
import com.mr.mr_api.common.jwt.JwtFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private JwtFilter jwtFilter;
  @Autowired
  private MrExceptionHandler mrExceptionHandler;

  /**
   * @TITLE password encoder(bcrypt)
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }
  
  /**
   * @TITLE set security config
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        // csrf 비활성화
        .csrf().disable()
        
        // Access Exception 설정
        .exceptionHandling()
          .authenticationEntryPoint(mrExceptionHandler)
          .accessDeniedHandler(mrExceptionHandler)
        
        // sesstion 설정
        .and()
          .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 비활성화
        
        // 권한 설정
        .and()
          .authorizeRequests() // http요청에 대한 접근 제한을 설정하겠다는 선언
          .antMatchers("/api/v1/users/*").permitAll() // user api는 모두 접근 허용
          // .antMatchers("/api/v1/users/**").hasAnyRole("USER", "MASTER") // ex: user요청에는 해당 설정 권한을 요구함
          .antMatchers("/api/v1/users/reservation/time-list").authenticated() // ex:해당경로는 로그인을 요구함
        
        // filter 설정
        .and()
          .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // jwt filter 적용
        ;
	}

}