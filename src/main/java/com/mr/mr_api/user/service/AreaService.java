package com.mr.mr_api.user.service;

import java.util.HashMap;
import java.util.Map;

import com.mr.mr_api.common.consts.Const;
import com.mr.mr_api.common.entity.ResEnt;
import com.mr.mr_api.common.handler.ResHandler;
import com.mr.mr_api.user.dto.area.AreaGuListCnt;
import com.mr.mr_api.user.dto.code.CodeListSvc;
import com.mr.mr_api.user.repository.CodeRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AreaService {
  
  @Autowired
  ResHandler resHandler;
  @Autowired
  CodeRepository codeRepository;
  @Autowired
  ModelMapper mpr;

  public ResponseEntity<ResEnt> getAreaSiList() {

    // set group
    CodeListSvc codeListSvc = new CodeListSvc();
    codeListSvc.setGroup(Const.CD_G_SI.val);

    Map<String, Object> result = new HashMap<>();
    result.put("list", codeRepository.getList(codeListSvc));
    log.info("Log : Get Area(Si) List Success");
    return resHandler.ok(result, HttpStatus.OK);
  }

  public ResponseEntity<ResEnt> getAreaGuList(AreaGuListCnt areaGuListCnt) {

    // set refId
    CodeListSvc codeListSvc = new CodeListSvc();
    codeListSvc.setRefId(areaGuListCnt.getId());
    codeListSvc.setGroup(Const.CD_G_GU.val);

    Map<String, Object> result = new HashMap<>();
    result.put("list", codeRepository.getList(codeListSvc));
    log.info("Log : Get Area(Gu) List Success");
    return resHandler.ok(result, HttpStatus.OK);
  }

}