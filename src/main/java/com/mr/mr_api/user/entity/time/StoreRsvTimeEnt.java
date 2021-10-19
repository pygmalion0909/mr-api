package com.mr.mr_api.user.entity.time;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StoreRsvTimeEnt {
  private String storeRsvDayId;
  private String rsvTm;
  private String dayCd;
  private String createTms;
}