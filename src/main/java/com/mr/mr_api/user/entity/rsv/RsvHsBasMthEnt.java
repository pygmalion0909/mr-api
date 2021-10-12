package com.mr.mr_api.user.entity.rsv;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RsvHsBasMthEnt {
  private String storeId;
  private String dayCd;
  private String rsvDt;
  private int countTime;
}