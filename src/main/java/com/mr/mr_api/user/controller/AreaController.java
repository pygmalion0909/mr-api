package com.mr.mr_api.user.controller;

import javax.validation.Valid;

import com.mr.mr_api.common.entity.ResEnt;
import com.mr.mr_api.common.handler.ResHandler;
import com.mr.mr_api.user.dto.area.AreaGuListCnt;
import com.mr.mr_api.user.service.AreaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/areas")
public class AreaController {

  @Autowired
  AreaService areaService;
  @Autowired
  ResHandler resHandler;

  /**
   * @TITLE 지역(시) 리스트 조회
   */
  @GetMapping("/area-si")
  public ResponseEntity<ResEnt> getAreaSiList() {
    return areaService.getAreaSiList();
  }

  /**
   * @TITLE 지역(구, 군) 리스트 조회
   * 필수파라미터 : 지역(시)ID
   */
  @GetMapping("/area-gu")
  public ResponseEntity<ResEnt> getAreaGuList(@Valid AreaGuListCnt areaGuListCnt) {
    return areaService.getAreaGuList(areaGuListCnt);
  }

}