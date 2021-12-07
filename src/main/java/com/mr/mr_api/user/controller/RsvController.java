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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/users/reservation")
@Slf4j
public class RsvController {

  @Autowired
  RsvService rsvService;

  /**
   * @TITLS 가게 예약 요일(영업요일) 리스트
   */
  @GetMapping("/day/{storeId}")
  public ResponseEntity<ResEnt> getWorkDayList(@PathVariable("storeId") String storeId) {
    return rsvService.getWorkDayList(storeId);
  }

  /**
   * @TITLE 예약 완료된 날짜 리스트
   */
  @GetMapping("/full-date")
  public ResponseEntity<ResEnt> getFullRsvDateList(@Valid RsvDtListCnt rsvDateListCnt) throws ParseException {
    return rsvService.getFullRsvDateList(rsvDateListCnt);
  }

  /**
   * @TITLE 예약 시간리스트
   */
  @GetMapping("/time")
  public ResponseEntity<ResEnt> getRsvTimeList(@Valid RsvTimeListCnt RsvTimeListCntDto) throws ParseException {
    return rsvService.getRsvTimeList(RsvTimeListCntDto);
  }

  /**
   * @TITLE 예약 아이템리스트
   */
  @GetMapping("/item")
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
  public void getRsvList() {}

  // 예약 수정 (/reservation/reservationId)
  // 예약 정보수정, 예약 삭제
  @PutMapping("")
  public void updateRsv() {}

}