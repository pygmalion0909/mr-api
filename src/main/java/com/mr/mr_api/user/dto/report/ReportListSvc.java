package com.mr.mr_api.user.dto.report;

import com.mr.mr_api.common.dto.Paging;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReportListSvc extends Paging{
  private String group;
}