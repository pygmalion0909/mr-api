package com.mr.mr_api.user.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SignupSvc {
  private String roleCd;
  private String loginId;
  private String nickName;
  private String passwd;
  private String email;
  private String statusCd;
  private String authKey;
}