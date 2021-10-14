package com.mr.mr_api.user.repository;

import java.util.List;

import com.mr.mr_api.user.dto.code.CodeListSvc;
import com.mr.mr_api.user.entity.code.CodeListEnt;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CodeRepository {

  public List<CodeListEnt> getList(CodeListSvc codeListSvc);
  
}