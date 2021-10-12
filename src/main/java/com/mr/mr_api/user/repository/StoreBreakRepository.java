package com.mr.mr_api.user.repository;

import java.util.List;

import com.mr.mr_api.user.dto.storeBreak.StoreBreakDtSvc;
import com.mr.mr_api.user.entity.storeBreak.StoreBreakDtEnt;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StoreBreakRepository {

  public List<StoreBreakDtEnt> getListOfMonth(StoreBreakDtSvc storeBreakDtSvc);
  
}