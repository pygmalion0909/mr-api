package com.mr.mr_api.user.controller;

import com.mr.mr_api.common.entity.ResEnt;
import com.mr.mr_api.user.service.BadgeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 공통코드에서 조회
 * 그룹은 필수로 하고
 * code리스트 조회
 * 1.배지 code
 * 1.시,도
 * 1.sector(기능추가로 인해 잠시미룸)
 */
@RestController
@RequestMapping("/api/v1/users/badges")
public class BadgeContorller {

  @Autowired
  BadgeService badgeService;
  
  /**
   * @TITLE 배지 리스트 조회
   */
  @GetMapping("")
  public ResponseEntity<ResEnt> getList() {
    return badgeService.getList();
  }

}