package com.mr.mr_api.user.repository;

import java.util.List;

import com.mr.mr_api.user.dto.report.ReportDetailSvc;
import com.mr.mr_api.user.dto.report.ReportDisplaySvc;
import com.mr.mr_api.user.dto.report.ReportListSvc;
import com.mr.mr_api.user.entity.report.ReportDetailEnt;
import com.mr.mr_api.user.entity.report.ReportDisplayEnt;
import com.mr.mr_api.user.entity.report.ReportListEnt;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReportRepository {

  public List<ReportListEnt> getList(ReportListSvc reportListSvc);
  public int getListTotal(ReportListSvc reportListSvc);
  public ReportDetailEnt getDetail(ReportDetailSvc resportDetailSvc);
  public List<ReportDisplayEnt> getDisplay(ReportDisplaySvc reportDisplaySvc);

}