package com.mr.mr_api.user.dto.store;

import com.mr.mr_api.common.dto.Paging;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreBasDto extends Paging {
  private String id;
  private String userId;
  private String name;
  private String address;
  private String areaSiCd;
  private String areaGuCd;
  private String number;
  private String sectorCd;
  private String createDt;
  private String modifyDt;
  private String deleteDt;
  private String wdRsvCycleCd;
  private String weRsvCycleCd;
  private String wdStWkTm;
  private String wdEdWkTm;
  private String weStWkTm;
  private String weEdWkTm;
  private String search;
  private String badgeCd; 
}