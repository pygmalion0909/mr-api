package com.mr.mr_api.user.service;

import java.util.HashMap;
import java.util.Map;

import com.mr.mr_api.common.entity.ResEnt;
import com.mr.mr_api.common.handler.ResHandler;
import com.mr.mr_api.user.dto.code.CodeDto;
import com.mr.mr_api.user.repository.CodeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CodeService {
  
  @Autowired
  ResHandler resHandler;
  @Autowired
  CodeRepository codeRepository;
  
  public ResponseEntity<ResEnt> getList(CodeDto codeDto) {
    Map<String, Object> result = new HashMap<>();
    result.put("list", codeRepository.getList(codeDto));
    return resHandler.ok(result, HttpStatus.OK);
  }

}