package com.mr.mr_api.user.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserSecretSvc {
  private String loginId;
  private String statusCd;
}