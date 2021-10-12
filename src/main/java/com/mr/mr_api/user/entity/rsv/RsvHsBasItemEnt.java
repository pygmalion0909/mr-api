package com.mr.mr_api.user.entity.rsv;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RsvHsBasItemEnt {
  private String id;
  private String storeId;
  private String itemId;
  private String userId;
  private String rsvDate;
  private String dayCd;
  private int rsvPer;
  private String statusCd;
  private String createDt;
  private String modifyDt;
  private int perTm;
  private int rsvPerSum;
}