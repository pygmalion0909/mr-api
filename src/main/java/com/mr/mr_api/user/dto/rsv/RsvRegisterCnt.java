package com.mr.mr_api.user.dto.rsv;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.mr.mr_api.common.consts.Verif;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RsvRegisterCnt {
  @NotBlank(message = Verif.NOTBLANK)
  private String storeId;
  private String userId;
  @NotBlank(message = Verif.NOTBLANK)
  private String itemId;
  @NotBlank(message = Verif.NOTBLANK)
  private String rsvDt;
  @NotBlank(message = Verif.NOTBLANK)
  private String rsvTm;
  @NotBlank(message = Verif.NOTBLANK)
  private String dayCd;
  @Min(value = 1, message = Verif.MIN)
  private int rsvPer;
}