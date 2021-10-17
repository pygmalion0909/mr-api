package com.mr.mr_api.user.dto.store;

import com.mr.mr_api.common.dto.Paging;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StoreListCnt extends Paging {
  private String search;
  private String sectorCd;
  private String subSectorCd;
  private String areaSiCd;
  private String[] areaGuCd;
  private String[] badgeCd; 
  private String imgGroup;
  private String sectorGroup;
}