package com.mr.mr_api.user.dto.rsv;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RsvHsBasMthSvc {
  private String storeId;
  private String yearMth;
  private String statusCd;
}