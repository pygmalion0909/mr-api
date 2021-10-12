package com.mr.mr_api.user.dto.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ItemRsvSvc {
  private String id;
  private String storeId;
  private String dayCd;
  private String createTms;
}