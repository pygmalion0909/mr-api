package com.mr.mr_api.user.entity.item;

import java.util.List;

import com.mr.mr_api.common.consts.Const;
import com.mr.mr_api.user.entity.person.PersonOptionEnt;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RsvItemDayEnt {
  private String dayCd;
  private String storeRsvDayId;
  private String itemId;
  private String storeId;
  private String name;
  private String desc;
  private String price;
  private String rsvStatusYn = Const.STATUS_CD_Y.val;
  private List<PersonOptionEnt> personOption;
  private int currentRsvPer;
  private String imgUrl;
}