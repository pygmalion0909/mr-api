package com.mr.mr_api.user.dto.user;

import javax.validation.constraints.Email;

import com.mr.mr_api.common.consts.Verif;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDulpCnt {
  @Email(message=Verif.FORMAT)
  private String email;
  private String loginId;
  private String nickName;
}