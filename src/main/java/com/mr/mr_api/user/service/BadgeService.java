package com.mr.mr_api.user.service;

import java.util.HashMap;
import java.util.Map;

import com.mr.mr_api.common.consts.Const;
import com.mr.mr_api.common.entity.ResEnt;
import com.mr.mr_api.common.handler.ResHandler;
import com.mr.mr_api.user.repository.BadgeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BadgeService {

  @Autowired
  ResHandler resHandler;
  @Autowired
  BadgeRepository badgeRepository;

  public ResponseEntity<ResEnt> getList() {
    Map<String, Object> result = new HashMap<>();
    result.put("list", badgeRepository.getList(Const.BADGE_GU.val));
    return resHandler.ok(result, HttpStatus.OK);
  }

}