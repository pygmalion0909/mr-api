package com.mr.mr_api.user.entity.time;

import java.util.List;

import com.mr.mr_api.common.consts.Const;
import com.mr.mr_api.user.entity.person.PersonOptionEnt;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RsvTimeDayEnt {
  private String id;
  private String storeRsvDayId;
  private String rsvTm;
  private String rsvStatusYn = Const.STATUS_CD_Y.val;;
  private int currentRsvPer;
  private List<PersonOptionEnt> personOption;
  private String createTms;
}