package com.mr.mr_api.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MailSendSvc {
  private String docName;
  private String nickName;
  private String authKey;
  private String loginId;
  private String subject;
  private String toAddr;
}