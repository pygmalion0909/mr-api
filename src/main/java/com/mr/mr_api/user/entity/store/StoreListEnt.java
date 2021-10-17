package com.mr.mr_api.user.entity.store;

import java.util.List;

import com.mr.mr_api.user.dto.badge.BadgeInfoDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreListEnt {
  private String id;
  private String userId;
  private String name;
  private String address;
  private String areaSiCd;
  private String areaGuCd;
  private String number;
  private String sectorCd;
  private String sectorName;
  private String createDt;
  private String modifyDt;
  private String imgUrl;
  private List<BadgeInfoDto> badgeList;
}