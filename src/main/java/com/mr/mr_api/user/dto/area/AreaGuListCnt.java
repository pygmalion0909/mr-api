package com.mr.mr_api.user.dto.area;

import javax.validation.constraints.NotBlank;

import com.mr.mr_api.common.consts.Verif;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaGuListCnt {
  @NotBlank(message = Verif.NOTBLANK)
  private String id;
}