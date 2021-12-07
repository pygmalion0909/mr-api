package com.mr.mr_api.user.repository;

import java.util.List;

import com.mr.mr_api.user.dto.time.StoreRsvDayTimeSvc;
import com.mr.mr_api.user.dto.time.StoreRsvTimeSvc;
import com.mr.mr_api.user.entity.time.RsvTimeAfterNowEnt;
import com.mr.mr_api.user.entity.time.RsvTimeDayEnt;
import com.mr.mr_api.user.entity.time.StoreRsvTimeEnt;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StoreRsvTimeRepository {
  
  public StoreRsvTimeEnt getOne(StoreRsvTimeSvc storeRsvTimeSvc);
  public List<RsvTimeDayEnt> getListOfDay(StoreRsvDayTimeSvc storeRsvDayTimeSvc);
  public List<RsvTimeAfterNowEnt> getListAfterNowTime(@Param("storeId") String storeId, @Param("dayCd") String dayCd, @Param("nowTime") String nowTime);

}