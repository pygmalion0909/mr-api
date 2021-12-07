package com.mr.mr_api.user.repository;

import java.util.List;

import com.mr.mr_api.user.dto.item.StoreRsvDayItemSvc;
import com.mr.mr_api.user.dto.item.StoreRsvItemSvc;
import com.mr.mr_api.user.entity.item.RsvItemDayEnt;
import com.mr.mr_api.user.entity.item.StoreRsvItemEnt;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StoreRsvItemRepository {
  
  public StoreRsvItemEnt getOne(StoreRsvItemSvc storeRsvItemSvc);
  public List<StoreRsvItemEnt> getList(StoreRsvItemSvc storeRsvItemSvc);
  public List<RsvItemDayEnt> getListOfDay(StoreRsvDayItemSvc storeRsvDayItemSvc);

}