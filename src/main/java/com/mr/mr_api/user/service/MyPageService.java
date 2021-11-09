package com.mr.mr_api.user.service;

import java.util.HashMap;
import java.util.Map;

import com.mr.mr_api.common.consts.Const;
import com.mr.mr_api.common.entity.ResEnt;
import com.mr.mr_api.common.handler.ResHandler;
import com.mr.mr_api.user.dto.user.UserOneSvc;
import com.mr.mr_api.user.entity.user.UserOneEnt;
import com.mr.mr_api.user.repository.MemberRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MyPageService {

  @Autowired
  private ModelMapper mpr;
  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private ResHandler resHandler;
  
  public ResponseEntity<ResEnt> getMyInfo(String cd, String loginId) {
    
    Map<String, Object> result = new HashMap<>();

    // get user info
    UserOneSvc userOneSvc = new UserOneSvc();
    userOneSvc.setLoginId(loginId);
    UserOneEnt userOneEnt = memberRepository.getOne(userOneSvc);

    // short user info
    if(cd.equals(Const.MY_INFO_HOME.val)) {
      UserOneEnt userShortInfo = new UserOneEnt();
      userShortInfo.setNickName(userOneEnt.getNickName());
      result.put("info", userShortInfo);
      return resHandler.ok(result, HttpStatus.OK);
    } 

    // create return obj
    result.put("info", userOneEnt);
    return resHandler.ok(result, HttpStatus.OK);
  }

}