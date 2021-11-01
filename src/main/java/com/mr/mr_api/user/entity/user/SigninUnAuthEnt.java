package com.mr.mr_api.user.entity.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SigninUnAuthEnt {
  private String loginId;
  private String nickName;
  private String email;
  private String statusCd;
}