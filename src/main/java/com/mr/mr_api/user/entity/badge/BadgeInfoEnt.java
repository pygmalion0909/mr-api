package com.mr.mr_api.user.entity.badge;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BadgeInfoEnt {
  private String storeId;
  private String cd;
  private int score;
  private String name;
}