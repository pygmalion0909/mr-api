package com.mr.mr_api.user.dto.person;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PersonOptionSvc {
  private String storeRsvTimeId;
  private String storeRsvItemId;
  private String numPerson;
  private String maxYn;
  private String minYn;
  private String perPersonYn;
  private String limitYn;
}