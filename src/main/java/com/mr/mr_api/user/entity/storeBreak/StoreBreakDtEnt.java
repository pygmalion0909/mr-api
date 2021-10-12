package com.mr.mr_api.user.entity.storeBreak;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StoreBreakDtEnt {
  private String storeId;
  private String bkStDt;
  private String bkEdDt;
  private String createTms;
}