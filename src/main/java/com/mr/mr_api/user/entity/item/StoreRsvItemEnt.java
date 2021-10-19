package com.mr.mr_api.user.entity.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StoreRsvItemEnt {
  private String id; 
  private String storeId; 
  private String storeRsvDayId; 
  private String itemId; 
  private String dayCd; 
  private String createTms; 
}