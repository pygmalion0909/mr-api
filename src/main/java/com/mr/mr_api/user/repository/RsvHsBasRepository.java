package com.mr.mr_api.user.repository;

import java.util.List;

import com.mr.mr_api.user.entity.rsv.RsvHsBasItemEnt;
import com.mr.mr_api.user.entity.rsv.RsvHsBasMthEnt;
import com.mr.mr_api.user.dto.rsv.RsvHsBasMthSvc;
import com.mr.mr_api.user.dto.rsv.RsvHsBasSvc;
import com.mr.mr_api.user.dto.rsv.RsvRegisterSvc;
import com.mr.mr_api.user.dto.rsv.RsvSumPerSvc;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RsvHsBasRepository {

  public List<RsvHsBasItemEnt> getRsvCompletedItems(RsvHsBasSvc rsvHsBasSvc);
  public List<RsvHsBasMthEnt> getListMonth(RsvHsBasMthSvc rsvHsBasMthSvc);
  public String getRsvSumPer(RsvSumPerSvc rsvSumPerSvc);
  public void register(RsvRegisterSvc rsvRegisterSvc);
  
}