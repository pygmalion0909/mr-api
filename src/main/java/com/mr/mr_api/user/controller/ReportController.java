package com.mr.mr_api.user.controller;

import com.mr.mr_api.common.dto.Paging;
import com.mr.mr_api.common.entity.ResEnt;
import com.mr.mr_api.common.handler.ResHandler;
import com.mr.mr_api.user.service.ReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/reports")
public class ReportController {

  @Autowired
  ReportService reportService;
  @Autowired
  ResHandler resHandler;

  /**
   * @TITLE 공지사항 리스트 조회
   */
  @GetMapping("")
  public ResponseEntity<ResEnt> getList(Paging paging) {
    return reportService.getList(paging);
  }

  /**
   * @TITLE 공지사항 상세조회
   */
  @GetMapping("/{reportId}")
  public ResponseEntity<ResEnt> getDetail(@PathVariable("reportId") String reportId) {
    return reportService.getDetail(reportId);
  }

  /**
   * @TITLE 공지사항 전시 리스트
   */
  @GetMapping("/display")
  public ResponseEntity<ResEnt> getDisplay() {
    return reportService.getDisplay();
  }

}