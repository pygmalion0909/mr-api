package com.mr.mr_api.user.repository;

import java.util.List;

import com.mr.mr_api.user.dto.badge.BadgeDto;
import com.mr.mr_api.user.dto.badge.BadgeInfoDto;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BadgeRepository {
  public List<BadgeDto> getList(String group);
  public List<BadgeInfoDto> getListOfInfo(String storeId);
}