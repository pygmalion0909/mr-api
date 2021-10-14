package com.mr.mr_api.user.service;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import com.mr.mr_api.common.consts.Const;
import com.mr.mr_api.common.consts.ResCd;
import com.mr.mr_api.common.dto.MailSendSvc;
import com.mr.mr_api.common.entity.ResEnt;
import com.mr.mr_api.common.handler.ResHandler;
import com.mr.mr_api.common.jwt.JwtTokenProvider;
import com.mr.mr_api.common.utils.CustomUserDetailService;
import com.mr.mr_api.common.utils.MailProvider;
import com.mr.mr_api.common.utils.UuidProvider;
import com.mr.mr_api.user.dto.user.UserOneSvc;
import com.mr.mr_api.user.dto.user.UserSecretSvc;
import com.mr.mr_api.user.dto.user.ReAuthKeyCnt;
import com.mr.mr_api.user.dto.user.ReqAuthPasswdSvc;
import com.mr.mr_api.user.dto.user.SearchIdCnt;
import com.mr.mr_api.user.dto.user.SearchPasswdCnt;
import com.mr.mr_api.user.dto.user.SigninCnt;
import com.mr.mr_api.user.dto.user.SignupAuthCnt;
import com.mr.mr_api.user.dto.user.SignupCnt;
import com.mr.mr_api.user.dto.user.SignupSvc;
import com.mr.mr_api.user.dto.user.UpdatePasswdCnt;
import com.mr.mr_api.user.dto.user.UpdatePasswdSvc;
import com.mr.mr_api.user.dto.user.UserDulpCnt;
import com.mr.mr_api.user.entity.user.SigninUnAuthEnt;
import com.mr.mr_api.user.entity.user.UserOneEnt;
import com.mr.mr_api.user.entity.user.UserSecretEnt;
import com.mr.mr_api.user.repository.MemberRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthService {

  @Autowired
  private ModelMapper mpr;
  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  CustomUserDetailService customUserDetailService;
  @Autowired
  private JwtTokenProvider jwtTokenProvider;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private UuidProvider uuidProvider;
  @Autowired
  private MailProvider mailProvider;
  @Autowired
  private ResHandler resHandler;
  
  public ResponseEntity<ResEnt> signin(SigninCnt signinCnt) {
    
    // create return obj
    Map<String, Object> result = new HashMap<>();
    
    // check user(loginId)
    UserSecretSvc userSecretSvc = mpr.map(signinCnt, UserSecretSvc.class);
    UserSecretEnt user = memberRepository.getOneOfSecret(userSecretSvc);
    if(user == null) return resHandler.err(ResCd.NOT_MEMBER, HttpStatus.BAD_REQUEST);

    // check role 
    if (!user.getRoleCd().equals(Const.ROLE_USER.val)) return resHandler.err(ResCd.NOT_MEMBER, HttpStatus.BAD_REQUEST);

    // check password
    if (!passwordEncoder.matches(signinCnt.getPasswd(), user.getPasswd())) {
      return resHandler.err(ResCd.NOT_MEMBER, HttpStatus.BAD_REQUEST);
    }

    // check statusCd(A, P)
    if(user.getStatusCd().equals(Const.STATUS_CD_A.val) || user.getStatusCd().equals(Const.STATUS_CD_P.val)) {
      SigninUnAuthEnt signinUnAuthEnt = new SigninUnAuthEnt();
      signinUnAuthEnt.setLoginId(user.getLoginId());
      signinUnAuthEnt.setNickName(user.getNickName());
      signinUnAuthEnt.setEmail(user.getEmail());
      result.put("info", signinUnAuthEnt);
      result.put("token", null);
      return resHandler.ok(result, HttpStatus.OK);
    }

    // create token
    String token = jwtTokenProvider.createToken(user.getLoginId());

    log.info("Log : Signin Successful");
    result.put("token", token);
    return resHandler.ok(result, HttpStatus.OK);
  }

  @Transactional
  public ResponseEntity<ResEnt> signup(SignupCnt signupCnt) throws MessagingException {

    // check duplication
    UserOneSvc userOneSvc = mpr.map(signupCnt, UserOneSvc.class);
    userOneSvc.setRoleCd(Const.ROLE_USER.val);
    int userCount = memberRepository.getCountOfDupl(userOneSvc);
    if(userCount > 0) return resHandler.err(ResCd.DUPL, HttpStatus.BAD_REQUEST);

    // user save obj mapping
    SignupSvc signupSvc = mpr.map(signupCnt, SignupSvc.class);
    signupSvc.setRoleCd(Const.ROLE_USER.val);
    signupSvc.setStatusCd(Const.STATUS_CD_A.val);

    // set password encryption
    String passwd = passwordEncoder.encode(signupCnt.getPasswd());
    signupSvc.setPasswd(passwd);

    // create uuid(auth key)
    String authKey = uuidProvider.create();
    signupSvc.setAuthKey(authKey);

    // save user
    memberRepository.signup(signupSvc);

    // send auth email
    MailSendSvc mailSendSvc = new MailSendSvc();
    mailSendSvc.setSubject(Const.SIGNUP_SUBJECT.val);
    mailSendSvc.setToAddr(signupCnt.getEmail());
    mailSendSvc.setNickName(signupCnt.getNickName());
    mailSendSvc.setDocName(Const.SIGNUP_DOC.val);
    mailSendSvc.setAuthKey(authKey);
    mailProvider.send(mailSendSvc);

    log.info("Log : User Signup Successful");
    return resHandler.ok(HttpStatus.OK);
  }

  @Transactional
  public ResponseEntity<ResEnt> signupCertification(SignupAuthCnt signupAuthCnt) {

    // check user(loginId, authKey)
    UserOneSvc userOneSvc = mpr.map(signupAuthCnt, UserOneSvc.class);
    userOneSvc.setStatusCd(Const.STATUS_CD_A.val);
    UserOneEnt user = memberRepository.getOne(userOneSvc);
    if(user == null) return resHandler.err(ResCd.NOT_MEMBER, HttpStatus.BAD_REQUEST);

    // update user info (authKey = null, approvalTms, statusCd = Y)
    memberRepository.updateSignupAuth(signupAuthCnt.getLoginId(), Const.STATUS_CD_Y.val);
    
    log.info("Log : Signup Certification Successful");
    return resHandler.ok(HttpStatus.OK);
  }

  public ResponseEntity<ResEnt> reDispatchAuthKey(ReAuthKeyCnt reAuthKeyCnt) throws MessagingException {
    
    // check user
    UserOneSvc userOneSvc = mpr.map(reAuthKeyCnt, UserOneSvc.class);
    UserOneEnt user = memberRepository.getOne(userOneSvc);
    if(user == null) return resHandler.err(ResCd.NOT_MEMBER, HttpStatus.BAD_REQUEST);
    if(!user.getStatusCd().equals(Const.STATUS_CD_A.val) && !user.getStatusCd().equals(Const.STATUS_CD_P.val)) {
      return resHandler.err(ResCd.NOT_MEMBER, HttpStatus.BAD_REQUEST);
    }

    // create uuid(auth key)
    String authKey = uuidProvider.create();
    
    // send auth key email
    MailSendSvc mailSendSvc = new MailSendSvc();
    switch (reAuthKeyCnt.getTarget()) {
      case "signup":
      mailSendSvc.setSubject(Const.SIGNUP_RE_SUBJECT.val);
      mailSendSvc.setDocName(Const.SIGNUP_DOC.val);
      break;
      case "passwd":
      mailSendSvc.setSubject(Const.PASSWD_RE_SUBJECT.val);
      mailSendSvc.setDocName(Const.PASSWD_DOC.val);
      break;
    }  
    mailSendSvc.setToAddr(user.getEmail());
    mailSendSvc.setNickName(user.getNickName());
    mailSendSvc.setAuthKey(authKey);
    mailProvider.send(mailSendSvc);

    // update user auth key
    memberRepository.updateAuthKey(reAuthKeyCnt.getLoginId(), authKey);

    log.info("Log : Re Dispatch Auth Key Successful");
    return resHandler.ok(HttpStatus.OK);
  }

  public ResponseEntity<ResEnt> searchLoginId(SearchIdCnt searchIdCnt) throws MessagingException {
    
    // search user
    UserOneSvc userOneSvc = mpr.map(searchIdCnt, UserOneSvc.class);
    userOneSvc.setRoleCd(Const.ROLE_USER.val);
    UserOneEnt user = memberRepository.getOne(userOneSvc);
    if(user == null) return resHandler.err(ResCd.NOT_MEMBER, HttpStatus.BAD_REQUEST);

    // set mail info and send
    MailSendSvc mail = new MailSendSvc();
    mail.setSubject(Const.LOGINID_SUBJECT.val);
    mail.setDocName(Const.LOGINID_DOC.val);
    mail.setToAddr(user.getEmail());
    mail.setNickName(user.getNickName());
    mail.setLoginId(user.getLoginId());
    mailProvider.send(mail);

    log.info("Log : Search LoginId Successful");
    return resHandler.ok(HttpStatus.OK);
  }

  @Transactional
  public ResponseEntity<ResEnt> searchPasswd(SearchPasswdCnt searchPasswdCnt) throws MessagingException {
    
    // search user
    UserOneSvc userOneSvc = mpr.map(searchPasswdCnt, UserOneSvc.class);
    UserOneEnt user = memberRepository.getOne(userOneSvc);
    if(user == null) return resHandler.err(ResCd.NOT_MEMBER, HttpStatus.BAD_REQUEST);

    // create auth-key
    String authKey = uuidProvider.create();
    
    // set mail info and send
    MailSendSvc mail = new MailSendSvc();
    mail.setSubject(Const.PASSWD_SUBJECT.val);
    mail.setDocName(Const.PASSWD_DOC.val);
    mail.setToAddr(user.getEmail());
    mail.setNickName(user.getNickName());
    mail.setAuthKey(authKey);
    mailProvider.send(mail);

    // update user statusCd (P) & auth key
    ReqAuthPasswdSvc reqAuthPasswdSvc = mpr.map(searchPasswdCnt, ReqAuthPasswdSvc.class);
    reqAuthPasswdSvc.setAuthKey(authKey);
    reqAuthPasswdSvc.setStatusCd(Const.STATUS_CD_P.val);
    memberRepository.updateReqAuthPasswd(reqAuthPasswdSvc);

    log.info("Log : SearchPaswwd Successful");
    return resHandler.ok(HttpStatus.OK);
  }

  @Transactional
  public ResponseEntity<ResEnt> updatePasswd(UpdatePasswdCnt updatePasswdCnt) {
    
    // search user(loginId, email, authKey) 
    UserOneSvc userOneSvc = mpr.map(updatePasswdCnt, UserOneSvc.class);
    UserOneEnt user = memberRepository.getOne(userOneSvc);
    if(user == null) return resHandler.err(ResCd.NOT_MEMBER, HttpStatus.BAD_REQUEST);

    // set password obj
    UpdatePasswdSvc updatePasswdSvc = mpr.map(updatePasswdCnt, UpdatePasswdSvc.class);

    // set password encryption
    String passwd = passwordEncoder.encode(updatePasswdCnt.getPasswd());
    updatePasswdSvc.setPasswd(passwd);

    // update password, statusCd
    updatePasswdSvc.setStatusCd(Const.STATUS_CD_Y.val);
    memberRepository.updatePasswd(updatePasswdSvc);

    log.info("Log : User Passwd Update Successful");
    return resHandler.ok(HttpStatus.OK);
  }

  public ResponseEntity<ResEnt> checkDuplication(UserDulpCnt userDuplCnt) {
    
    // search user
    UserOneSvc userOneSvc = mpr.map(userDuplCnt, UserOneSvc.class);
    if(userDuplCnt.getEmail() != null) userOneSvc.setRoleCd(Const.ROLE_USER.val);
    UserOneEnt user = memberRepository.getOne(userOneSvc);

    // is duplication
    if(user != null) return resHandler.err(ResCd.DUPL, HttpStatus.BAD_REQUEST);
    
    log.info("Log : Check Duplication Successful");
    return resHandler.ok(HttpStatus.OK);
  }

}