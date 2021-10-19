package com.mr.mr_api.user.dto.file;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileSvc {
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