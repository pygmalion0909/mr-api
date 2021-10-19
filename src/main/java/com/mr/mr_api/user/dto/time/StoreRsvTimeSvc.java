package com.mr.mr_api.user.dto.time;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StoreRsvTimeSvc {
  private String storeId;
  private String dayCd;
  private String rsvTm;
}