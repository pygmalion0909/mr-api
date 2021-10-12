package com.mr.mr_api.user.repository;

import java.util.List;

import com.mr.mr_api.user.dto.store.StoreBasDto;
import com.mr.mr_api.user.dto.store.StoreListDto;
import com.mr.mr_api.user.dto.store.StoreOneDto;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StoreBasRepository {
  
  public List<StoreListDto> getListOfInfo(StoreBasDto storeBasDto);
  public int getTotal();
  public StoreOneDto getOne(String storeId);
  public StoreOneDto getStoreDetailImg(String storeId);

}