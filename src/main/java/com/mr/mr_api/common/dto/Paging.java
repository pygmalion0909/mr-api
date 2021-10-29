package com.mr.mr_api.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Paging {
  private int offset = 0;
  private int limit = 12;
}