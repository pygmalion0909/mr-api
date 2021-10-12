package com.mr.mr_api.user.dto.user;

import javax.validation.constraints.NotBlank;

import com.mr.mr_api.common.consts.Verif;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SigninCnt {
  @NotBlank(message = Verif.NOTBLANK)
  private String loginId;
  @NotBlank(message = Verif.NOTBLANK)
  private String passwd;
}