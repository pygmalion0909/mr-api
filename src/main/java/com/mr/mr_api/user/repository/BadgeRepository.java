package com.mr.mr_api.user.repository;

import java.util.List;

import com.mr.mr_api.user.dto.badge.BadgeInfoDto;
import com.mr.mr_api.user.dto.badge.BadgeListSvc;
import com.mr.mr_api.user.entity.badge.BadgeListEnt;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BadgeRepository {

  public List<BadgeListEnt> getList(BadgeListSvc badgeListSvc);
  public List<BadgeInfoDto> getListOfInfo(String storeId);

}