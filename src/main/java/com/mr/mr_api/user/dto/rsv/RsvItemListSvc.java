package com.mr.mr_api.user.dto.rsv;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RsvItemListSvc {
  private String storeId;
  private String dayCd;
  private String rsvTms;
}