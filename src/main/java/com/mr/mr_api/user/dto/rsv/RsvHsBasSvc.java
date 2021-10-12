package com.mr.mr_api.user.dto.rsv;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RsvHsBasSvc {
  private String id;
  private String storeId;
  private String itemId;
  private String userId;
  private String rsvTms;
  private String dayCd;
  private int rsvPer;
  private String statusCd;
  private String createTms;
  private String modifyTms;
}