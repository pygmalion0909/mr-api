package com.mr.mr_api.user.entity.person;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PersonOptionEnt {
  private int numPerson;
  private String maxYn;
  private String minYn;
  private String perPersonYn;
  private String limitYn;
}