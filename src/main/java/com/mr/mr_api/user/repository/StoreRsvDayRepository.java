package com.mr.mr_api.user.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StoreRsvDayRepository {

  public List<String> getListOfWorkDay(String storeId);

}