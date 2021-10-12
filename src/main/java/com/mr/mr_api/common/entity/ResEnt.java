package com.mr.mr_api.common.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResEnt {
  private int status;
  private Map<String, Object> data;
  private String errCd;
  private String errMsg;
  private List<Object> errList;
  private Date time;
}