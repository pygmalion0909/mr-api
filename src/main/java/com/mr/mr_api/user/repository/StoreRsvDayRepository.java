package com.mr.mr_api.user.repository;

import java.util.List;

import com.mr.mr_api.user.entity.day.StoreBreakDayEnt;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StoreRsvDayRepository {

  public List<StoreBreakDayEnt> getListOfBreakDay(String group);

}