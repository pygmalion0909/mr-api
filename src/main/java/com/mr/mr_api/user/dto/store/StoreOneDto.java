package com.mr.mr_api.user.dto.store;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreOneDto {
  private String id;
  private String userId;
  private String name;
  private String address;
  private String areaSiCd;
  private String areaSiName;
  private String areaGuCd;
  private String areaGuName;
  private String number;
  private String sectorCd;
  private String sectorName;
  private String createDt;
  private String modifyDt;
  private String wdRsvCycleCd;
  private String weRsvCycleCd;
  private String wdStWkTm;
  private String wdEdWkTm;
  private String weStWkTm;
  private String weEdWkTm; 
}