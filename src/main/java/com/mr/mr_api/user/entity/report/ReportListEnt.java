package com.mr.mr_api.user.entity.report;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReportListEnt {
  private String id;
  private String categoryCd;
  private String title;
  private String shortContents;
  private String createTms;
  private String modifyTms;
  private String name;
}