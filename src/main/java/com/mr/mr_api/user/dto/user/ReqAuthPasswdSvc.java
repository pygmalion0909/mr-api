package com.mr.mr_api.user.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReqAuthPasswdSvc {
  private String email;
  private String loginId;
  private String authKey;
  private String statusCd;
}