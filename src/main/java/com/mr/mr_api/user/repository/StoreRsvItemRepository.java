package com.mr.mr_api.user.repository;

import com.mr.mr_api.user.dto.item.StoreRsvItemSvc;
import com.mr.mr_api.user.entity.item.StoreRsvItemEnt;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StoreRsvItemRepository {
  
  public StoreRsvItemEnt getOne(StoreRsvItemSvc storeRsvItemSvc);

}