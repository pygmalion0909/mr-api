package com.mr.mr_api.user.repository;

import java.util.List;

import com.mr.mr_api.user.dto.person.PersonOptionSvc;
import com.mr.mr_api.user.entity.person.PersonOptionEnt;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PersonOptionRepository {

  public List<PersonOptionEnt> getList(PersonOptionSvc personOptionSvc);
  
}