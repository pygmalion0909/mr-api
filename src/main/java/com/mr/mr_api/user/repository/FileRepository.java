package com.mr.mr_api.user.repository;

import java.util.List;

import com.mr.mr_api.user.dto.file.FileSvc;
import com.mr.mr_api.user.entity.file.FileEnt;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileRepository {

  public List<FileEnt> getList(FileSvc fileSvc);

}