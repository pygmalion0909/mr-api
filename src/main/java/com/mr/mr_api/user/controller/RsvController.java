package com.mr.mr_api.user.controller;

import java.text.ParseException;

import javax.validation.Valid;

import com.mr.mr_api.common.annotation.AuthInfo;
import com.mr.mr_api.common.dto.AuthSvc;
import com.mr.mr_api.common.entity.ResEnt;
import com.mr.mr_api.user.dto.rsv.RsvDtListCnt;
import com.mr.mr_api.user.dto.rsv.RsvItemListCnt;
import com.mr.mr_api.user.dto.rsv.RsvRegisterCnt;
import com.mr.mr_api.user.service.RsvService;
import com.mr.mr_api.user.dto.rsv.RsvTimeListCnt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/reservation")
public class RsvController {

  @Autowired
  RsvService rsvService;

  /**
   * @TITLE 예약 불가능한 날짜&요일 리스트
   */
  @GetMapping("/date-list")
  public ResponseEntity<ResEnt> getRsvDateList(@Valid RsvDtListCnt rsvDateListCnt) throws ParseException {
    return rsvService.getRsvDateList(rsvDateListCnt);
  }

  /**
   * @TITLE 예약 시간리스트
   */
  @GetMapping("/time-list")
  public ResponseEntity<ResEnt> getRsvTimeList(@Valid RsvTimeListCnt RsvTimeListCntDto) {
    return rsvService.getRsvTimeList(RsvTimeListCntDto);
  }

  /**
   * @TITLE 예약 아이템리스트
   */
  @GetMapping("/item-list")
  public ResponseEntity<ResEnt> getRsvItemList(@Valid RsvItemListCnt rsvItemListCntDto) {
    return rsvService.getRsvItemList(rsvItemListCntDto);
  }

  /**
   * @TITLE 예약 등록
   */
  @PostMapping("")
  public ResponseEntity<ResEnt> registerRsv(@AuthInfo AuthSvc auth, @Valid @RequestBody RsvRegisterCnt rsvRegisterCnt) {
    rsvRegisterCnt.setUserId(auth.getId());
    return rsvService.registerRsv(rsvRegisterCnt);
  }
  
  // 예약 조회 (/reservation/reservationId)
  @GetMapping("")
  public void getRsvList() {
  }

  // 예약 수정 (/reservation/reservationId)
  // 예약 정보수정, 예약 삭제
  @PutMapping("")
  public void updateRsv() {}

}