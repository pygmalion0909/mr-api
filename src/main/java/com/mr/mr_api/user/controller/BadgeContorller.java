package com.mr.mr_api.user.controller;

import com.mr.mr_api.common.entity.ResEnt;
import com.mr.mr_api.user.service.BadgeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/badges")
public class BadgeContorller {

  @Autowired
  BadgeService badgeService;
  
  /**
   * @TITLE 뱃지 리스트 조회
   */
  @GetMapping("")
  public ResponseEntity<ResEnt> getList() {
    return badgeService.getList();
  }

  /**
   * @TITLE 가게 뱃지 레벨, 스코어 조회
   */
  @GetMapping("/{storeId}")
  public ResponseEntity<ResEnt> getListScoreLv(@PathVariable("storeId") String storeId) {
    return badgeService.getListScoreLv(storeId);
  }

}