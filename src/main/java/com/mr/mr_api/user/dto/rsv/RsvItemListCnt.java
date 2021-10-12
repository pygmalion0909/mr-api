package com.mr.mr_api.user.dto.rsv;

import javax.validation.constraints.NotBlank;

import com.mr.mr_api.common.consts.Verif;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RsvItemListCnt {
  @NotBlank(message = Verif.NOTBLANK)
  private String storeId;
  @NotBlank(message = Verif.NOTBLANK)
  private String dayCd;
  @NotBlank(message = Verif.NOTBLANK)
  private String rsvTms;
}