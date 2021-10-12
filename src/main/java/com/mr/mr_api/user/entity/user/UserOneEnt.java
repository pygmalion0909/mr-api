package com.mr.mr_api.user.entity.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserOneEnt {
  private String id;
  private String roleCd;
  private String loginId;
  private String nickName;
  private String email;
  private String statusCd;
  private String authKey;
  private String createTms;
  private String approvalTms;
  private String modifyTms;
}