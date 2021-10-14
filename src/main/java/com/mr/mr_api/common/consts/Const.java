package com.mr.mr_api.common.consts;

public enum Const {

    GROUP_BADGE("BADGE") // 배지 그룹명
  , GROUP_BADGE_BG("BADGEBG") // 배지색상 그룹명
  , GROUP_STO_BAS_IMG("STBAS") // 상점기본이미지 그룹명
  , GROUP_AREA_GU("AREAGU") // 지역(구, 군) 그룹명
  , GROUP_SECTOR("SECTOR") // 가게 업종 그룹명
  , GROUP_SUBSECTOR("SUBSECTOR") // 가게 서브업종 그룹명
  , STATUS_CD_Y("Y") // yes
  , STATUS_CD_N("N") // no
  , STATUS_CD_A("A") // 인증 진행중
  , STATUS_CD_P("P") // 비밀번호 찾기 요청
  , CODE_G_DAY("DAY") // 코드그룹(요일)
  , CODE_G_SI("AREASI") // 코드그룹(지역(시))
  , CODE_G_GU("AREAGU") // 코드그룹(지역(구, 군))
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