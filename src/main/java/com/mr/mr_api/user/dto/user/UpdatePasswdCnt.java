package com.mr.mr_api.user.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.mr.mr_api.common.consts.Verif;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdatePasswdCnt {
  @NotBlank(message = Verif.NOTBLANK)
  private String authKey;
  @NotBlank(message = Verif.NOTBLANK)
  private String loginId;
  @NotBlank(message = Verif.NOTBLANK)
  @Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{6,}$", message=Verif.FORMAT)
  private String passwd;
  @NotBlank(message = Verif.NOTBLANK)
  @Email(message=Verif.FORMAT)
  private String email;
}