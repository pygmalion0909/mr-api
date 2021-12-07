package com.mr.mr_api.user.repository;

import java.util.List;

import com.mr.mr_api.user.dto.store.StoreListCnt;
import com.mr.mr_api.user.entity.store.StoreOneEnt;
import com.mr.mr_api.user.entity.store.StoreListEnt;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StoreBasRepository {
  
  public List<StoreListEnt> getListOfInfo(StoreListCnt storeListCnt);
  public int getTotal(StoreListCnt storeListCnt);
  public StoreOneEnt getOne(String storeId);

}