package com.mr.mr_api.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.validation.Valid;

import com.mr.mr_api.common.consts.Const;
import com.mr.mr_api.common.consts.ResCd;
import com.mr.mr_api.common.consts.Verif;
import com.mr.mr_api.common.entity.ResEnt;
import com.mr.mr_api.common.handler.ResHandler;
import com.mr.mr_api.user.dto.user.SignupAuthCnt;
import com.mr.mr_api.user.dto.user.ReAuthKeyCnt;
import com.mr.mr_api.user.dto.user.SearchIdCnt;
import com.mr.mr_api.user.dto.user.SearchPasswdCnt;
import com.mr.mr_api.user.dto.user.SigninCnt;
import com.mr.mr_api.user.dto.user.SignupCnt;
import com.mr.mr_api.user.dto.user.UpdatePasswdCnt;
import com.mr.mr_api.user.dto.user.UserDulpCnt;
import com.mr.mr_api.user.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/auths")
public class AuthContorller {

  @Autowired
  AuthService authService;
  @Autowired
  ResHandler resHandler;

  /**
   * @TITLE 로그인
   */
  @PostMapping("/signin")
  public ResponseEntity<ResEnt> signin(@Valid @RequestBody SigninCnt signinCnt) {
    return authService.signin(signinCnt);
  }

  /**
   * @TITLE 회원가입
   */
  @PostMapping("/signup")
  public ResponseEntity<ResEnt> signup(@Valid @RequestBody SignupCnt signupCnt) throws MessagingException {
    return authService.signup(signupCnt);
  }

  /**
   * @TITLE 회원가입 인증처리
   */
  @PutMapping("/signup/certification")
  public ResponseEntity<ResEnt> signupCertification(@Valid @RequestBody SignupAuthCnt signupAuthCnt) {
    return authService.signupCertification(signupAuthCnt);
  }

  /**
   * @TITLE 인증키 재요청
   */
  @PutMapping("/re-dispatch/auth-key")
  public ResponseEntity<ResEnt> reDispatchAuthKey(@Valid @RequestBody ReAuthKeyCnt reAuthKeyCnt) 
  throws MessagingException {
    
    // check target validation
    if(!reAuthKeyCnt.getTarget().equals(Const.RE_SEND_TARGET_SIGN.val) 
    && !reAuthKeyCnt.getTarget().equals(Const.RE_SEND_TARGET_PASS.val)) {
      List<Object> errList = new ArrayList<>();
      Map<String, String> errObj = new HashMap<>();
      errObj.put("field", "target");
      errObj.put("msg", Verif.NOTEXACT);
      errList.add(errObj);
      return resHandler.err(ResCd.VALIDATION, errList, HttpStatus.BAD_REQUEST);
    }

    return authService.reDispatchAuthKey(reAuthKeyCnt);
  }

  /**
   * @TITLE 아이디 찾기
   */
  @PostMapping("/search/login-id")
  public ResponseEntity<ResEnt> searchLoginId(@Valid @RequestBody SearchIdCnt searchIdCnt) 
  throws MessagingException {
    return authService.searchLoginId(searchIdCnt);
  }
  
  /**
   * @TITLE 비밀번호 찾기(인증메일)
   */
  @PutMapping("/search/passwd")
  public ResponseEntity<ResEnt> searchPasswd(@Valid @RequestBody SearchPasswdCnt searchPasswdCnt)
  throws MessagingException {
    return authService.searchPasswd(searchPasswdCnt);
  }

  /**
   * @TITLE 비밀번호 찾기(비밀번호 수정)
   */
  @PutMapping("/update/passwd")
  public ResponseEntity<ResEnt> updatePasswd(@Valid @RequestBody UpdatePasswdCnt updatePasswdCnt) {
    return authService.updatePasswd(updatePasswdCnt);
  }

  /**
   * @TITLE 이메일, 로그인ID, 닉네임 중복체크
   */
  @PostMapping("/duplication")
  public ResponseEntity<ResEnt> checkDuplication(@Valid @RequestBody UserDulpCnt userDuplCnt) {
    return authService.checkDuplication(userDuplCnt);
  }

}