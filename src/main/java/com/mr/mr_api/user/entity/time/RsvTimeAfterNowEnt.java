package com.mr.mr_api.user.entity.time;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RsvTimeAfterNowEnt {
  private String id;
  private String storeId;
  private String dayCd;
  private String rsvTm;
}