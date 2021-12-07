package com.mr.mr_api.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.mr.mr_api.common.annotation.AuthInfo;
import com.mr.mr_api.common.consts.Const;
import com.mr.mr_api.common.consts.ResCd;
import com.mr.mr_api.common.consts.Verif;
import com.mr.mr_api.common.dto.AuthSvc;
import com.mr.mr_api.common.entity.ResEnt;
import com.mr.mr_api.common.handler.ResHandler;
import com.mr.mr_api.user.service.MyPageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/mypage")
public class MyPageContorller {

  @Autowired
  private MyPageService myPageService;
  @Autowired
  private ResHandler resHandler;

  /**
   * @TITLE 나의정보 조회
   */
  @GetMapping("/info")
  public ResponseEntity<ResEnt> getMyInfo(@Valid String cd, @AuthInfo AuthSvc auth) {

    // check target validation
    if(!cd.equals(Const.MY_INFO_HOME.val) && !cd.equals(Const.MY_INFO_MYPAGE.val)) {
      List<Object> errList = new ArrayList<>();
      Map<String, String> errObj = new HashMap<>();
      errObj.put("field", "target");
      errObj.put("msg", Verif.NOTEXACT);
      errList.add(errObj);
      return resHandler.err(ResCd.VALIDATION, errList, HttpStatus.BAD_REQUEST);
    }

    return myPageService.getMyInfo(cd, auth.getUsername());
  }

}