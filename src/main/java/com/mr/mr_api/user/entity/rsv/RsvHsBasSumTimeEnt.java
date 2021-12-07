package com.mr.mr_api.user.entity.rsv;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RsvHsBasSumTimeEnt {
  private String id;
  private String storeId;
  private String itemId;
  private String userId;
  private String rsvTms;
  private String dayCd;
  private String rsvPer;
  private String statusCd;
  private String createTms;
  private String modifyTms;
  private int sumRsvPer;
  private String storeRsvDayId;
  private String storeRsvTimeId;
}