package com.mr.mr_api.common.consts;

public enum ResCd {
    DUPL("0-00", "Parameter Duplication") 
  , NOT_DATA("0-01", "Not Found Data")
  , NULL("0-02", "Null Pointer")
  , VALIDATION("0-03", "Parameter Validation Failed")
  , NOT_MEMBER("0-04", "Not Found Member")
  , F_MAIL("0-05", "Failed to send Mail")
  , F_AUTH("0-06", "Forbidden")
  , F_RESERVATION("0-07", "Impossible Reservation")
  ;

  public String cd;
  public String msg;
  ResCd(String cd, String msg) {
    this.cd = cd;
    this.msg = msg;
  }

}