package com.mr.mr_api.common.handler;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mr.mr_api.common.consts.ResCd;
import com.mr.mr_api.common.entity.ResEnt;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ResHandler {

  /**
   * @TITLE response ok
   */
  public ResponseEntity<ResEnt> ok(HttpStatus status) {
    return new ResponseEntity<ResEnt>(setResEnt(status), status);
  }

  /**
   * @TITLE response ok(with data)
   */
  public ResponseEntity<ResEnt> ok(Map<String, Object> data, HttpStatus status) {
		ResEnt resEnt = setResEnt(status);
    resEnt.setData(data);
    return new ResponseEntity<ResEnt>(resEnt, status);
  }

  /**
   * @TITLE response err
   */
  public ResponseEntity<ResEnt> err(ResCd resCd, HttpStatus status) {
		ResEnt resEnt = setResEnt(status);
    resEnt.setErrCd(resCd.cd);
		resEnt.setErrMsg(resCd.msg);
		return new ResponseEntity<ResEnt>(resEnt, status);
  }
	
  /**
   * @TITLE response err(with err list)
   */
  public ResponseEntity<ResEnt> err(ResCd resCd, List<Object> errList, HttpStatus status) {
		ResEnt resEnt = setResEnt(status);
    resEnt.setErrCd(resCd.cd);
		resEnt.setErrMsg(resCd.msg);
    resEnt.setErrList(errList);
		return new ResponseEntity<ResEnt>(resEnt, status);
  }

	private ResEnt setResEnt(HttpStatus status) {
		ResEnt resEnt = new ResEnt();
		resEnt.setStatus(status.value());
		resEnt.setTime(new Date());
		return resEnt;
	}

}