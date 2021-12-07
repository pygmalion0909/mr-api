package com.mr.mr_api.user.dto.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StoreRsvDayItemSvc {
  private String dayCd;
  private String storeId;
  private String baseImgUrl;
  private String imgGroup;
}