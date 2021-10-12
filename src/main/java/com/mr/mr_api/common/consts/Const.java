package com.mr.mr_api.common.consts;

public enum Const {

    BADGE_GU("BADGE") // 배지그룹
  , ST_BAS_IMG_GU("STBAS") // 상점기본이미지그룹
  , STATUS_CD_Y("Y") // yes
  , STATUS_CD_N("N") // no
  , STATUS_CD_A("A") // 인증진행중
  , STATUS_CD_P("P") // 비밀번호 찾기 요청
  , CODE_G_DAY("DAY") // 코드그룹요일
  , ROLE_USER("ROLE_USER") // 역할 일반회원
  , ROLE_CEO("ROLE_CEO") // 역할 사장님
  , ROLE_MASTER("ROLE_MASTER") // 역할 관리자
  , SIGNUP_SUBJECT("Make Reservation 회원가입 인증요청") // 회원가입 인증 메일 제목
  , SIGNUP_RE_SUBJECT("Make Reservation 회원가입 인증 재요청") // 회원가입 인증 메일 재요청 제목
  , LOGINID_SUBJECT("Make Reservation 아이디 찾기 요청") // 아이디찾기 메일 제목
  , PASSWD_SUBJECT("Make Reservation 비밀번호 찾기 인증번호 요청") // 비밀번호 찾기 인증번호 메일 제목
  , PASSWD_RE_SUBJECT("Make Reservation 비밀번호 찾기 인증번호 재요청") // 비밀번호 찾기 인증번호 메일 재요청 제목
  , SIGNUP_DOC("SignupAuth") // 회원가입 메일 문서 이름
  , LOGINID_DOC("LoginIdSearch") // 로그인찾기 메일 문서 이름
  , PASSWD_DOC("PasswdSearch") // 비밀번호찾기 메일 문서 이름
  , RE_SEND_TARGET_SIGN("signup") // 인증키 재전송 타켓
  , RE_SEND_TARGET_PASS("passwd") // 인증키 재전송 타켓
  ;

  public String val;
  Const(String val) {
    this.val = val;
  }

}