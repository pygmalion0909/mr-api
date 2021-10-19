package com.mr.mr_api.user.entity.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ItemBasEnt {
  private String id;
  private String storeId;
  private String name;
  private String desc;
  private int perTm;
  private String price;
  private String createTms;
  private String modifyTms;
}