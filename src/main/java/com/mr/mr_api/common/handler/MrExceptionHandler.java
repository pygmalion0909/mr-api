package com.mr.mr_api.common.handler;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import com.mr.mr_api.common.consts.ResCd;
import com.mr.mr_api.common.entity.ResEnt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

@RestControllerAdvice
public class MrExceptionHandler implements AccessDeniedHandler, AuthenticationEntryPoint {

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired
  ResHandler resHandler;
  
  /**
   * @TITLE Null Pointer Exception
   * TODO
   * 1. 서버 문제로 인한 에러는 프론트에서 상세히 알수없게 메세지 보내기
   * 2. log파일로 기록 남기기
   * 3. 모든 예외 처리방법 구상하기!!!
   */
  @ExceptionHandler(NullPointerException.class)
  public ResponseEntity<ResEnt> handleNullException(NullPointerException e) {
    log.info("⭐⭐⭐ NullPointerException Position ⭐⭐⭐ : {}", e.getStackTrace());
    return resHandler.err(ResCd.NULL, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * @TITLE date parse Exception
   */
  @ExceptionHandler(ParseException.class)
  public ResponseEntity<ResEnt> handleParseException(ParseException e) {
    log.info("⭐⭐⭐ ParseException Position ⭐⭐⭐ : {}", e.getStackTrace());
    return resHandler.err(ResCd.NULL, HttpStatus.INTERNAL_SERVER_ERROR);
  }
  
  /**
   * @TITLE member not found Exception
   */
  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<ResEnt> handleMemberNotFoundException(UsernameNotFoundException e) {
    log.info("⭐⭐⭐ MemeberNotFoundException Position ⭐⭐⭐ : {}", e.getStackTrace());
    return resHandler.err(ResCd.NOT_MEMBER, HttpStatus.BAD_REQUEST);
  }

  /**
   * @TITLE send mail Exception
   */
  @ExceptionHandler(MessagingException.class)
  public ResponseEntity<ResEnt> handleMailException(MessagingException e) {
    log.info("⭐⭐⭐ Mail Exception Position ⭐⭐⭐ : {}", e.getStackTrace());
    return resHandler.err(ResCd.F_MAIL, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * @TITLE Validation Fail Exception
   */
  @ExceptionHandler(BindException.class)
  public ResponseEntity<ResEnt> handleValidException(BindException e) {
    /**
     * bindR.getErrorCount(): 에러 총 개수
     * bindR.getFieldErrors(): 에러 정보 리스트
     * el.getField(): 필드 이름
     * el.getDefaultMessage(): 필드별 에러 메세지
     */
    BindingResult bindR = e.getBindingResult();
    List<FieldError> bindRList = bindR.getFieldErrors();

    List<Object> errList = new ArrayList<>();
    for(final FieldError el: bindRList) {
      Map<String, String> errObj = new HashMap<>();
      errObj.put("field", el.getField());
      errObj.put("msg", el.getDefaultMessage());
      errList.add(errObj);
    }
    return resHandler.err(ResCd.VALIDATION, errList, HttpStatus.BAD_REQUEST);
  }

  /**
   * @TITLE 인증은 되었으나 인가되지 않은 권한의 요청일 경우(AccessDeniedHandler)
   * (ex: 일반회원이 마스터권한에 요청 할 경우)
   */
  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
  throws IOException, ServletException {
    handleAuthException(response);
  }

  /**
   * @TITLE 인증이 필요한 요청에 인증하지 않고 요청한 경우(AuthenticationEntryPoint)
   * (ex: 비로그인)
   */
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
  throws IOException, ServletException {
    handleAuthException(response);
  }

  private void handleAuthException (HttpServletResponse response) throws IOException {
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpStatus.FORBIDDEN.value());
    
    try (OutputStream os = response.getOutputStream()) {
      ObjectMapper objectMapper = new ObjectMapper();
      ResEnt resEnt = new ResEnt();
      resEnt.setStatus(HttpStatus.FORBIDDEN.value());
      resEnt.setErrCd(ResCd.F_AUTH.cd);
      resEnt.setErrMsg(ResCd.F_AUTH.msg);
      objectMapper.writeValue(os, resEnt);
      os.flush();
    }
  }

}