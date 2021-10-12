package com.mr.mr_api.common.utils;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class UuidProvider {

  public String create() {
    String uuid = UUID.randomUUID().toString();
    return uuid;
  }

}