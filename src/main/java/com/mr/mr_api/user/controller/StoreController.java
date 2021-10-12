package com.mr.mr_api.user.controller;

import com.mr.mr_api.common.entity.ResEnt;
import com.mr.mr_api.user.dto.store.StoreBasDto;
import com.mr.mr_api.user.service.StoreService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 * 카테고리 추가 하기
 * 한식, 중식, 일식, 양식, 기타, 대학병원, 한의원, 등등....
 * 업종이랑 별개로 하기
 */
@RestController
@RequestMapping("/api/v1/users/stores")
public class StoreController {

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired
  StoreService storeService;

  /**
   * @TITLE 가게 리스트 조회
   * TODO
   * 1.검색검증필요
   * 1.이미지BASEURL주소 환경변수에 설정(WebConfig포함)
   */
  @GetMapping("")
  public ResponseEntity<ResEnt> getStoreList(StoreBasDto storeBasDto) {
    return storeService.getList(storeBasDto);
  }

  /**
   * @TITLE 가게 상세 조회
   */
  @GetMapping("/bas/{storeId}")
  public ResponseEntity<ResEnt> getStoreOne(@PathVariable("storeId") String storeId) {
    return storeService.getOne(storeId);
  }

  /**
   * @TITLE 가게 상세 이미지 조회
   */
  @GetMapping("/bas/img/{storeId}")
  public ResponseEntity<ResEnt> getStoreDetailImg(@PathVariable("storeId") String storeId) {
    return storeService.getStoreDetailImg(storeId);
  }

  /**
   * @title 가게 등록
   */
  @PostMapping("/")
  public void registerStore() {

  }

  /**
   * @title 가게 수정
   * 정보변경(가게 정보변경)
   * 가게 삭제(상태값 변경) 진짜 삭제는 bacth돌려서
   */
  @PutMapping("/{storeId}")
  public void updateStore() {

  }

}