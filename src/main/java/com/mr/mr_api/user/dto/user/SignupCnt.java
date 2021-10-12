package com.mr.mr_api.user.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.mr.mr_api.common.consts.Verif;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SignupCnt {
  @NotBlank(message=Verif.NOTBLANK)
  @Size(max=25, message=Verif.OVERSIZE)
  private String loginId;

  @NotBlank(message=Verif.NOTBLANK)
  @Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{6,}$", message=Verif.FORMAT)
  private String passwd;

  @NotBlank(message=Verif.NOTBLANK)
  @Size(max=50, message=Verif.OVERSIZE)
  @Email(message=Verif.FORMAT)
  private String email;

  @NotBlank(message=Verif.NOTBLANK)
  @Size(max=25, message=Verif.OVERSIZE)
  private String nickName;
}