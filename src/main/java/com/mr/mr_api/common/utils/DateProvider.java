package com.mr.mr_api.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateProvider {
  
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public void yyyyMMddHHMM() {
    SimpleDateFormat test = new SimpleDateFormat("yyyy-MM-dd HHmm");
    SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    try {
      Date test1 = test.parse("2021-09-27 0900");
      String formatDate = form.format(test1);
      log.info("test1 : {}", test1);
      log.info("formatDate : {}",formatDate);
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
}
