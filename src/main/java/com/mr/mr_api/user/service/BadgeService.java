package com.mr.mr_api.user.service;

import java.util.HashMap;
import java.util.Map;

import com.mr.mr_api.common.consts.Const;
import com.mr.mr_api.common.entity.ResEnt;
import com.mr.mr_api.common.handler.ResHandler;
import com.mr.mr_api.user.dto.badge.BadgeListSvc;
import com.mr.mr_api.user.repository.BadgeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BadgeService {

  @Autowired
  ResHandler resHandler;
  @Autowired
  BadgeRepository badgeRepository;

  public ResponseEntity<ResEnt> getList() {
    
    BadgeListSvc badgeListSvc = new BadgeListSvc();
    badgeListSvc.setBadgeCd(Const.GROUP_BADGE.val);
    badgeListSvc.setColorCd(Const.GROUP_BADGE_BG.val);

    Map<String, Object> result = new HashMap<>();
    result.put("list", badgeRepository.getList(badgeListSvc));
    
    log.info("Log : Badge List Successful");
    return resHandler.ok(result, HttpStatus.OK);
  }

}