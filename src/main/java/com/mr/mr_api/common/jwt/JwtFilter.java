package com.mr.mr_api.common.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class JwtFilter extends GenericFilterBean {

  @Autowired
  JwtTokenProvider jwtTokenProvider;

  /**
   * @TITLE security context user 정보 저장
   * Request의 token을 가져와 해당 token의 유효한지 체크 후, secruity context에 user정보를 저장하는 filter
   * 해당 filter는 security filter에 등록해야 정상 작동 가능함
   */
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException { 
   
    String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

    if (token != null && jwtTokenProvider.validateToken(token)) {
      Authentication authentication = jwtTokenProvider.getAuthentication(token);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    
    chain.doFilter(request, response);
  }

}