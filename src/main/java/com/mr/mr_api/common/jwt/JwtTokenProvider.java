package com.mr.mr_api.common.jwt;

import java.util.Base64;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.mr.mr_api.common.utils.CustomUserDetailService;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Component
public class JwtTokenProvider implements InitializingBean {

  @Autowired
  CustomUserDetailService customUserDetailService;
  
  // token 생성 및 해독 key
  private final String secretKey;
  // token key to base64
  private String secretKeyB64;
  // token 유효시간
  private final long tokenValidityInMSc;
  // token header key
  private final String headerKey;
  
  public JwtTokenProvider(
    @Value("${jwt.secret-key}") String secretKey,
    @Value("${jwt.token-validity-in-sc}") long tokenValidityInSc,
    @Value("${jwt.header-key}") String headerKey
  ) {
    this.secretKey = secretKey;
    this.tokenValidityInMSc = tokenValidityInSc * 1000;
    this.headerKey = headerKey;
  }

  /**
   * @TITLE secretKey encode(string to base64)
   */
  @Override
  public void afterPropertiesSet() {
    secretKeyB64 = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  /**
   * @TITLE jwt token 생성
   */
  public String createToken(String loginId) {

    // user정보 등록
    Claims claims = Jwts.claims().setSubject(loginId);

    // token유효기간 생성
    long now = (new Date()).getTime();
    Date validityMSc = new Date(now + this.tokenValidityInMSc);

    // token생성
    return Jwts.builder()
               .setClaims(claims) // token에 정보 등록
               .setIssuedAt(new Date()) // token 생성일
               .setExpiration(validityMSc) // 유효기간 설정
               .signWith(SignatureAlgorithm.HS256, secretKeyB64) // token생성 방식
               .compact()
               ;
  }

  /**
   * @TITLE token으로 user정보 얻기
   * token을 해석하여 user정보를 얻고, security context에 저장하기 위한 객체로 return함.
   */
  public Authentication getAuthentication(String token) {
    UserDetails userDetails = customUserDetailService.loadUserByUsername(this.parsingToken(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  /**
   * @TITLE token 분석으로 body정보(loginId) 얻기
   */
  private String parsingToken(String token) {
    return Jwts.parser()
               .setSigningKey(secretKeyB64)
               .parseClaimsJws(token)
               .getBody()
               .getSubject()
               ;
  }

  /**
   * @TITLE Http request에서 token 가져오기
   */
  public String resolveToken(HttpServletRequest request) {
    return request.getHeader(headerKey);
  }

  /**
   * @TITLE token 유효시간 검사
   */
  public boolean validateToken(String token) {
    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(secretKeyB64).parseClaimsJws(token);
      return !claims.getBody().getExpiration().before(new Date());
    } catch (Exception e) {
      return false;
    }
  }

}