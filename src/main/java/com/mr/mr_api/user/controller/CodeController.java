package com.mr.mr_api.user.controller;

import com.mr.mr_api.common.consts.ResCd;
import com.mr.mr_api.common.entity.ResEnt;
import com.mr.mr_api.common.handler.ResHandler;
import com.mr.mr_api.user.dto.code.CodeDto;
import com.mr.mr_api.user.service.CodeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/codes")
public class CodeController {
  
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired
  CodeService codeService;
  @Autowired
  ResHandler resHandler;

  /**
   * @TITLE 공통코드 리스트 조회
   */
  @GetMapping("")
  public ResponseEntity<ResEnt> getList(CodeDto codeDto) {
    if(codeDto.getGroup() == null || codeDto.getGroup().trim().equals("")) {
      return resHandler.err(ResCd.VALIDATION, HttpStatus.BAD_REQUEST);
    }
    return codeService.getList(codeDto);
  }

}