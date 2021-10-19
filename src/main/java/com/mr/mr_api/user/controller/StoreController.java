package com.mr.mr_api.user.controller;

import javax.validation.Valid;

import com.mr.mr_api.common.entity.ResEnt;
import com.mr.mr_api.user.dto.store.StoreListCnt;
import com.mr.mr_api.user.dto.store.SubSectorListCnt;
import com.mr.mr_api.user.service.StoreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/stores")
public class StoreController {

  @Autowired
  StoreService storeService;

  /**
   * @TITLE 가게 리스트 조회
   */
  @GetMapping("")
  public ResponseEntity<ResEnt> getStoreList(StoreListCnt storeListCnt) {
    return storeService.getStoreList(storeListCnt);
  }

  /**
   * @TITLE 가게 상세 조회
   */
  @GetMapping("/bas/{storeId}")
  public ResponseEntity<ResEnt> getStoreOne(@PathVariable("storeId") String storeId) {
    return storeService.getStoreOne(storeId);
  }

  /**
   * @TITLE 가게 상세 이미지 조회
   */
  @GetMapping("/bas/imgs/{storeId}")
  public ResponseEntity<ResEnt> getStoreBasImgs(@PathVariable("storeId") String storeId) {
    return storeService.getStoreBasImgs(storeId);
  }

  /**
   * @TITLE 가게 업종 리스트 조회
   */
  @GetMapping("/sectors")
  public ResponseEntity<ResEnt> getSectorList() {
    return storeService.getSectorList();
  }

  /**
   * @TITLE 가게 서브 업종 리스트 조회
   */
  @GetMapping("/sub-sectors")
  public ResponseEntity<ResEnt> getSubSectorList(@Valid SubSectorListCnt subSectorListCnt) {
    return storeService.getSubSectorList(subSectorListCnt);
  }

}