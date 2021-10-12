package com.mr.mr_api.user.repository;

import java.util.List;

import com.mr.mr_api.user.dto.time.StoreRsvDayTimeSvc;
import com.mr.mr_api.user.entity.time.TimeRsvEnt;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StoreRsvTimeRepository {
  public List<TimeRsvEnt> getListOfDay(StoreRsvDayTimeSvc storeRsvDayTimeSvcDto);
}