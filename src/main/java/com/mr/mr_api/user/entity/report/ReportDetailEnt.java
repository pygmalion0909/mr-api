package com.mr.mr_api.user.entity.report;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReportDetailEnt {
  private String id;
  private String title;
  private String contents;
  private String name;
  private String createTms;
  private String modifyTms;
  private String prevId;
  private String nextId;
  private String categoryCd;
}