package com.mr.mr_api.user.repository;

import java.util.List;

import com.mr.mr_api.user.entity.rsv.RsvHsBasMthEnt;
import com.mr.mr_api.user.entity.rsv.RsvHsBasOneEnt;
import com.mr.mr_api.user.entity.rsv.RsvHsBasSumPerEnt;
import com.mr.mr_api.user.entity.rsv.RsvHsBasSumTimeEnt;
import com.mr.mr_api.user.dto.rsv.RsvHsBasMthSvc;
import com.mr.mr_api.user.dto.rsv.RsvHsBasOneSvc;
import com.mr.mr_api.user.dto.rsv.RsvHsBasSumPerSvc;
import com.mr.mr_api.user.dto.rsv.RsvHsBasSumTimeSvc;
import com.mr.mr_api.user.dto.rsv.RsvRegisterSvc;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RsvHsBasRepository {

  public List<RsvHsBasOneEnt> getOne(RsvHsBasOneSvc rsvHsBasOneSvc);
  public List<RsvHsBasSumPerEnt> getListOfSumItem(RsvHsBasSumPerSvc rsvHsBasSumPerSvc);
  public List<RsvHsBasSumTimeEnt> getListOfSumTime(RsvHsBasSumTimeSvc rsvHsBasSumTimeSvc);
  public List<RsvHsBasMthEnt> getListMonth(RsvHsBasMthSvc rsvHsBasMthSvc);
  public void register(RsvRegisterSvc rsvRegisterSvc);
  
}