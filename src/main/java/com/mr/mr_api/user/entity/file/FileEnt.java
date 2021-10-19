package com.mr.mr_api.user.entity.file;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FileEnt {
  private String id;
  private String refId;
  private String group;
  private String originName;
  private String saveName;
  private String size;
  private String extension;
  private String createDt;
  private String modifyDt;
}