package com.mr.mr_api.user.repository;

import java.util.List;

import com.mr.mr_api.user.dto.item.ItemBasOneSvc;
import com.mr.mr_api.user.dto.item.ItemRsvSvc;
import com.mr.mr_api.user.entity.item.ItemBasEnt;
import com.mr.mr_api.user.entity.item.ItemRsvEnt;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemBasRepository {

  public ItemBasEnt getOne(ItemBasOneSvc itemBasOneSvc);
  public List<ItemRsvEnt> getListOfDay(ItemRsvSvc ItemRsvSvc);

}