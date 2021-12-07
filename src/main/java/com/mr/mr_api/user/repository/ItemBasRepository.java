package com.mr.mr_api.user.repository;

import com.mr.mr_api.user.dto.item.ItemBasOneSvc;
import com.mr.mr_api.user.entity.item.ItemBasEnt;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemBasRepository {

  public ItemBasEnt getOne(ItemBasOneSvc itemBasOneSvc);

}