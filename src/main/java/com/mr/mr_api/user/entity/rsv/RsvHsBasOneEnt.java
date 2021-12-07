package com.mr.mr_api.user.entity.rsv;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RsvHsBasOneEnt {

  private String id;
  private String storeId;
  private String userId;
  private String rsvName;
  private String rsvNumber;
  private String rsvReqest;
  private String itemId;
  private String rsvPrice;
  private String rsvTms;
  private String dayCd;
  private String rsvPer;
  private String statusCd;
  private String createTms;
  private String modifyTms;

}