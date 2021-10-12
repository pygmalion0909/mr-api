package com.mr.mr_api.user.repository;

import java.util.List;

import com.mr.mr_api.user.dto.file.FileDto;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileRepository {

  public List<FileDto> getList(FileDto fileDto);

}