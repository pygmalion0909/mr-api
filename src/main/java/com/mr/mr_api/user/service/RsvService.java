package com.mr.mr_api.user.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mr.mr_api.common.consts.Const;
import com.mr.mr_api.common.entity.ResEnt;
import com.mr.mr_api.common.handler.ResHandler;
import com.mr.mr_api.user.dto.rsv.RsvItemListCnt;
import com.mr.mr_api.user.dto.rsv.RsvTimeListCnt;
import com.mr.mr_api.user.dto.storeBreak.StoreBreakDtSvc;
import com.mr.mr_api.user.dto.time.StoreRsvDayTimeSvc;
import com.mr.mr_api.user.entity.day.StoreBreakDayEnt;
import com.mr.mr_api.user.entity.item.ItemRsvEnt;
import com.mr.mr_api.user.entity.rsv.RsvHsBasItemEnt;
import com.mr.mr_api.user.entity.rsv.RsvHsBasMthEnt;
import com.mr.mr_api.user.entity.rsv.RsvNoneDtEnt;
import com.mr.mr_api.user.entity.storeBreak.StoreBreakDtEnt;
import com.mr.mr_api.user.entity.time.TimeRsvEnt;
import com.mr.mr_api.user.repository.ItemBasRepository;
import com.mr.mr_api.user.repository.RsvHsBasRepository;
import com.mr.mr_api.user.repository.StoreBasRepository;
import com.mr.mr_api.user.repository.StoreBreakRepository;
import com.mr.mr_api.user.repository.StoreRsvDayRepository;
import com.mr.mr_api.user.repository.StoreRsvTimeRepository;
import com.mr.mr_api.user.dto.item.ItemRsvSvc;
import com.mr.mr_api.user.dto.rsv.RsvDtListCnt;
import com.mr.mr_api.user.dto.rsv.RsvHsBasMthSvc;
import com.mr.mr_api.user.dto.rsv.RsvHsBasSvc;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RsvService {

  private final Logger log = LoggerFactory.getLogger(this.getClass());
  
  @Autowired
  private ModelMapper mpr;
  @Autowired
  private ResHandler resHandler;
  @Autowired
  private ItemBasRepository itemBasRepository;
  @Autowired
  private RsvHsBasRepository rsvHsBasRepository;
  @Autowired
  private StoreBasRepository storeBasRepository;
  @Autowired
  private StoreRsvTimeRepository storeRsvTimeRepository;
  @Autowired
  private StoreBreakRepository storeBreakRepository;
  @Autowired
  private StoreRsvDayRepository storeRsvDayRepository;

  public ResponseEntity<ResEnt> getRsvDateList(RsvDtListCnt rsvDtListCnt) throws ParseException {
    // set return obj
    Map<String, Object> result = new HashMap<>();
    List<RsvNoneDtEnt> disableDt = new ArrayList<>();

    // 1. set break time all list
    List<RsvNoneDtEnt> bkTimeList = createStoreBreakTime(rsvDtListCnt);
    if(bkTimeList.size() > 0) disableDt.addAll(bkTimeList);
    log.info("bkTimeList : {}", bkTimeList);
    
    // 2. get reservation date on month
    RsvHsBasMthSvc rsvHsBasMthSvcDto = mpr.map(rsvDtListCnt, RsvHsBasMthSvc.class);
    rsvHsBasMthSvcDto.setStatusCd(Const.STATUS_CD_Y.val);
    List<RsvHsBasMthEnt> rsvHsBasMthEnt =  rsvHsBasRepository.getListMonth(rsvHsBasMthSvcDto);

    if(rsvHsBasMthEnt.size() <= 0) {
      result.put("list", null);
      return resHandler.ok(result, HttpStatus.OK);
    }
    
    // for문돌려서 dayCd로 시간 가져오기
    for(RsvHsBasMthEnt el : rsvHsBasMthEnt) {
      // get rsv time list of day
      StoreRsvDayTimeSvc storeRsvTimeSvcDto = mpr.map(el, StoreRsvDayTimeSvc.class);
      List<TimeRsvEnt> timeList = storeRsvTimeRepository.getListOfDay(storeRsvTimeSvcDto);
      log.info("timeList : {}", timeList);

      // 상세검증필요대상
      int noneTimeCount = 0;
      if(el.getCountTime() == timeList.size()) {
        ItemRsvSvc itemRsvSvc = mpr.map(el, ItemRsvSvc.class);
        List<TimeRsvEnt> nTargetList = getNoneRsvTimeList(itemRsvSvc, timeList, el.getRsvDt(), el.getDayCd());
        noneTimeCount = nTargetList.size();
      }

      if(noneTimeCount == timeList.size()) {
        RsvNoneDtEnt rsvNoneDtEnt = mpr.map(el, RsvNoneDtEnt.class);
        disableDt.add(rsvNoneDtEnt);
      }
    }

    if(disableDt.size() > 0) {
      result.put("dtList", disableDt); 
    } else {
      result.put("dtList", null); 
    }

    // 3. set break day list
    List<StoreBreakDayEnt> bkDayList = storeRsvDayRepository.getListOfBreakDay(Const.CODE_G_DAY.val);
    if(bkDayList.size() > 0) {
      result.put("dayList", bkDayList);
    }else{
      result.put("dayList", null); 
    }

    log.info("Log : Store Rsv None Date List Success");
    return resHandler.ok(result, HttpStatus.OK);
  }

  public ResponseEntity<ResEnt> getRsvTimeList(RsvTimeListCnt rsvTimeListCnt) {
    Map<String, Object> result = new HashMap<>();
    
    // get store rsv time list of day
    StoreRsvDayTimeSvc storeRsvTimeSvcDto = mpr.map(rsvTimeListCnt, StoreRsvDayTimeSvc.class);
    List<TimeRsvEnt> timeList = storeRsvTimeRepository.getListOfDay(storeRsvTimeSvcDto);
    
    if(timeList.size() <= 0) {
      result.put("list", null);
      return resHandler.ok(result, HttpStatus.OK);
    }
    
    // get impossible reservation time list
    ItemRsvSvc itemRsvSvc = mpr.map(rsvTimeListCnt, ItemRsvSvc.class);
    List<TimeRsvEnt> nTargetList = getNoneRsvTimeList(itemRsvSvc, timeList, rsvTimeListCnt.getRsvDt(), rsvTimeListCnt.getDayCd());
    
    // set time status
    for(TimeRsvEnt el : timeList) {
      for(TimeRsvEnt cEl : nTargetList) {
        if(cEl.getRsvTm().equals(el.getRsvTm())) el.setStatus(Const.STATUS_CD_N.val);
      }
      if(el.getStatus() == null) el.setStatus(Const.STATUS_CD_Y.val);
    }
    
    log.info("Log : Store Rsv Time List Success");
    result.put("list", timeList);
    return resHandler.ok(result, HttpStatus.OK); 
  }

  public ResponseEntity<ResEnt> getRsvItemList(RsvItemListCnt rsvItemListCntDto) {
    Map<String, Object> result = new HashMap<>();
    
    // get store reservation item list of day
    ItemRsvSvc itemRsvSvcDto = mpr.map(rsvItemListCntDto, ItemRsvSvc.class);
    List<ItemRsvEnt> itemList = itemBasRepository.getListOfDay(itemRsvSvcDto); 
    
    if(itemList.size() <= 0) {
      result.put("list", null);
      return resHandler.ok(result, HttpStatus.OK);
    }

    // get completed reservation item list
    RsvHsBasSvc rsvHsBasSvcDto = mpr.map(rsvItemListCntDto, RsvHsBasSvc.class);
    rsvHsBasSvcDto.setStatusCd(Const.STATUS_CD_Y.val);
    List<RsvHsBasItemEnt> rsvItemList = rsvHsBasRepository.getRsvCompletedItems(rsvHsBasSvcDto);

    // set item status
    for(ItemRsvEnt el : itemList) {
      for(RsvHsBasItemEnt cEl : rsvItemList) {
        if(el.getId().equals(cEl.getItemId())) el.setStatus(Const.STATUS_CD_N.val); 
      }
      if(el.getStatus() == null) el.setStatus(Const.STATUS_CD_Y.val);
    }
      
    log.info("Log : Store Rsv Item List Success");
    result.put("list", itemList);
    return resHandler.ok(result, HttpStatus.OK);
  }

  public void registerRsv() {
  }

  /**
   * @TITLE check impossible reservation time and get list
   */
  private List<TimeRsvEnt> getNoneRsvTimeList(ItemRsvSvc itemRsvSvc, List<TimeRsvEnt> timeList, String rsvDt, String dayCd) {
    // get store rsv item list of day
    List<ItemRsvEnt> itemList = itemBasRepository.getListOfDay(itemRsvSvc); 
    
    // get completed reservation item list and check item count
    List<TimeRsvEnt> nTargetList = new ArrayList<>();
    for(TimeRsvEnt el : timeList) {
      RsvHsBasSvc rsvHsBasSvc = new RsvHsBasSvc();
      rsvHsBasSvc.setRsvTms(rsvDt + " " + el.getRsvTm());
      rsvHsBasSvc.setDayCd(dayCd);
      rsvHsBasSvc.setStatusCd(Const.STATUS_CD_Y.val);
      List<RsvHsBasItemEnt> rsvItemList = rsvHsBasRepository.getRsvCompletedItems(rsvHsBasSvc);  

      if(itemList.size() == rsvItemList.size()) nTargetList.add(el);
    }

    return nTargetList;
  }

  /**
   * @TITLE create store break time list
   */
  private List<RsvNoneDtEnt> createStoreBreakTime(RsvDtListCnt rsvDtListCnt) throws ParseException {
    
    // get store break time list
    StoreBreakDtSvc storeBreakDtSvc = mpr.map(rsvDtListCnt, StoreBreakDtSvc.class);
    List<StoreBreakDtEnt> bkTime = storeBreakRepository.getListOfMonth(storeBreakDtSvc);
    
    // set date format
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    // set break time all list
    List<RsvNoneDtEnt> dates = new ArrayList<>();
    for(StoreBreakDtEnt el : bkTime) {
      Date startDt = sdf.parse(el.getBkStDt());
      Date endDt = sdf.parse(el.getBkEdDt());
      Date currentDt = startDt;
      
      while (currentDt.compareTo(endDt) <= 0) {
        RsvNoneDtEnt rsvNoneDtEnt = new RsvNoneDtEnt();
        rsvNoneDtEnt.setRsvDt(sdf.format(currentDt));
        dates.add(rsvNoneDtEnt);

        Calendar c = Calendar.getInstance();
        c.setTime(currentDt);
        c.add(Calendar.DAY_OF_MONTH, 1);
        currentDt = c.getTime();
      }
    }

    return dates;
  }

}