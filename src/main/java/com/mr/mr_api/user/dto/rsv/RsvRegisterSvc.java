package com.mr.mr_api.user.dto.rsv;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RsvRegisterSvc {
  private String storeId;
  private String userId;
  private String itemId;
  private String rsvName;
  private String rsvNumber;
  private String rsvRequest;
  private String rsvPrice;
  private String rsvTms;
  private String dayCd;
  private String rsvPer;
  private String statusCd;
}