package com.mr.mr_api.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mr.mr_api.common.consts.Const;
import com.mr.mr_api.common.dto.Paging;
import com.mr.mr_api.common.entity.ResEnt;
import com.mr.mr_api.common.handler.ResHandler;
import com.mr.mr_api.user.dto.report.ReportDetailSvc;
import com.mr.mr_api.user.dto.report.ReportDisplaySvc;
import com.mr.mr_api.user.dto.report.ReportListSvc;
import com.mr.mr_api.user.entity.report.ReportListEnt;
import com.mr.mr_api.user.repository.ReportRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReportService {
  
  @Autowired
  ResHandler resHandler;
  @Autowired
  ReportRepository reportRepository;
  @Autowired
  ModelMapper mpr;

  public ResponseEntity<ResEnt> getList(Paging paging) {

    // get list
    ReportListSvc reportListSvc = new ReportListSvc();
    reportListSvc.setGroup(Const.CD_G_REPORT.val);
    reportListSvc.setLimit(paging.getLimit());
    reportListSvc.setOffset(paging.getOffset());
    List<ReportListEnt> list = reportRepository.getList(reportListSvc);

    // get total
    int total = reportRepository.getListTotal(reportListSvc);
    
    Map<String, Object> result = new HashMap<>();
    result.put("list", list);
    result.put("total", total);
    
    log.info("Log : Report List Success");
    return resHandler.ok(result, HttpStatus.OK);
  }

  public ResponseEntity<ResEnt> getDetail(String reportId) {

    ReportDetailSvc reportDetailSvc = new ReportDetailSvc();
    reportDetailSvc.setGroup(Const.CD_G_REPORT.val);
    reportDetailSvc.setReportId(reportId);

    Map<String, Object> result = new HashMap<>();
    result.put("info", reportRepository.getDetail(reportDetailSvc));
    log.info("Log : Report Detail Success");
    return resHandler.ok(result, HttpStatus.OK);
  }

  public ResponseEntity<ResEnt> getDisplay() {
    ReportDisplaySvc reportDisplaySvc = new ReportDisplaySvc();
    reportDisplaySvc.setGroup(Const.CD_G_REPORT.val);
    reportDisplaySvc.setStatusCd(Const.STATUS_CD_Y.val);

    Map<String, Object> result = new HashMap<>();
    result.put("list", reportRepository.getDisplay(reportDisplaySvc));
    log.info("Log : Report Display Success");
    return resHandler.ok(result, HttpStatus.OK);
  }

}