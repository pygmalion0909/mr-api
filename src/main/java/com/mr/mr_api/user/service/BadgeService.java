package com.mr.mr_api.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mr.mr_api.common.consts.Const;
import com.mr.mr_api.common.entity.ResEnt;
import com.mr.mr_api.common.handler.ResHandler;
import com.mr.mr_api.user.dto.badge.BadgeListSvc;
import com.mr.mr_api.user.dto.code.CodeListSvc;
import com.mr.mr_api.user.entity.badge.BadgeInfoEnt;
import com.mr.mr_api.user.entity.badge.BadgeLvEnt;
import com.mr.mr_api.user.entity.code.CodeListEnt;
import com.mr.mr_api.user.repository.BadgeRepository;
import com.mr.mr_api.user.repository.CodeRepository;

import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BadgeService {

  @Autowired
  private ResHandler resHandler;
  @Autowired
  private BadgeRepository badgeRepository;
  @Autowired
  private CodeRepository codeRepository;
  @Autowired
  private ModelMapper mpr;

  public ResponseEntity<ResEnt> getList() {
    
    BadgeListSvc badgeListSvc = new BadgeListSvc();
    badgeListSvc.setBadgeCd(Const.CD_G_BADGE.val);

    Map<String, Object> result = new HashMap<>();
    result.put("list", badgeRepository.getList(badgeListSvc));
    
    log.info("Log : Badge List Successful");
    return resHandler.ok(result, HttpStatus.OK);
  }

  public ResponseEntity<ResEnt> getListScoreLv(String storeId) {
    
    // get badge list
    List<BadgeInfoEnt> badgeList = badgeRepository.getListOfStore(storeId, Const.CD_G_BADGE.val);
    
    // set badge level score
    List<BadgeLvEnt> badgeScoreLvList = new ArrayList<>();

    if(badgeList.size() > 0) {
      // get level score
      CodeListSvc codeListSvc = new CodeListSvc();
      codeListSvc.setGroup(Const.CD_G_BADGELV.val);
      List<CodeListEnt> lvScoreList = codeRepository.getList(codeListSvc);

      
      // set level
      int halfIndex = lvScoreList.size()/2;

      for(BadgeInfoEnt el : badgeList) {
        BadgeLvEnt badgeLvEl = mpr.map(el, BadgeLvEnt.class);

        if(el.getScore() < Integer.parseInt(lvScoreList.get(halfIndex).getName())) {
          // 중간 점수보다 작은경우
          badgeLvEl.setLevel(setBadgeLevel(0, halfIndex, el.getScore(), lvScoreList));
        }else {
          // 중간 점수보다 큰경우
          badgeLvEl.setLevel(setBadgeLevel(halfIndex, lvScoreList.size(), el.getScore(), lvScoreList));
        }
        badgeScoreLvList.add(badgeLvEl);
      }
    }
    
    Map<String, Object> result = new HashMap<>();
    result.put("list", badgeScoreLvList);
    log.info("Log : Badge Level Score List Successful");
    return resHandler.ok(result, HttpStatus.OK);
  }

  private int setBadgeLevel(int start, int limit, int badgeScore, List<CodeListEnt>lvScoreList) {

    int lv = 0;

    for(int i = start; i < limit; i++) {
      int lvScore = Integer.parseInt(lvScoreList.get(i).getName());
      if(badgeScore < lvScore) {
        lv= i;
        break;
      }else{
        lv = i + 1;
      }
    }

    return lv;
  }

}