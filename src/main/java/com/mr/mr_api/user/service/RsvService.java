package com.mr.mr_api.user.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.mr.mr_api.common.consts.Const;
import com.mr.mr_api.common.entity.ResEnt;
import com.mr.mr_api.common.handler.ResHandler;
import com.mr.mr_api.user.dto.rsv.RsvItemListCnt;
import com.mr.mr_api.user.dto.rsv.RsvRegisterCnt;
import com.mr.mr_api.user.dto.rsv.RsvRegisterSvc;
import com.mr.mr_api.user.dto.rsv.RsvTimeListCnt;
import com.mr.mr_api.user.dto.storeBreak.StoreBreakDtSvc;
import com.mr.mr_api.user.dto.time.StoreRsvDayTimeSvc;
import com.mr.mr_api.user.entity.item.RsvItemDayEnt;
import com.mr.mr_api.user.entity.item.StoreRsvItemEnt;
import com.mr.mr_api.user.entity.person.PersonOptionEnt;
import com.mr.mr_api.user.entity.rsv.RsvHsBasMthEnt;
import com.mr.mr_api.user.entity.rsv.RsvHsBasOneEnt;
import com.mr.mr_api.user.entity.rsv.RsvHsBasSumPerEnt;
import com.mr.mr_api.user.entity.rsv.RsvHsBasSumTimeEnt;
import com.mr.mr_api.user.entity.store.StoreOneEnt;
import com.mr.mr_api.user.entity.storeBreak.StoreBreakDtEnt;
import com.mr.mr_api.user.entity.time.RsvTimeAfterNowEnt;
import com.mr.mr_api.user.entity.time.RsvTimeDayEnt;
import com.mr.mr_api.user.repository.ItemBasRepository;
import com.mr.mr_api.user.repository.PersonOptionRepository;
import com.mr.mr_api.user.repository.RsvHsBasRepository;
import com.mr.mr_api.user.repository.StoreBasRepository;
import com.mr.mr_api.user.repository.StoreBreakRepository;
import com.mr.mr_api.user.repository.StoreRsvDayRepository;
import com.mr.mr_api.user.repository.StoreRsvItemRepository;
import com.mr.mr_api.user.repository.StoreRsvTimeRepository;
import com.mr.mr_api.user.dto.item.StoreRsvDayItemSvc;
import com.mr.mr_api.user.dto.item.StoreRsvItemSvc;
import com.mr.mr_api.user.dto.person.PersonOptionSvc;
import com.mr.mr_api.user.dto.rsv.RsvDtListCnt;
import com.mr.mr_api.user.dto.rsv.RsvHsBasMthSvc;
import com.mr.mr_api.user.dto.rsv.RsvHsBasOneSvc;
import com.mr.mr_api.user.dto.rsv.RsvHsBasSumPerSvc;
import com.mr.mr_api.user.dto.rsv.RsvHsBasSumTimeSvc;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  private StoreRsvTimeRepository storeRsvTimeRepository;
  @Autowired
  private StoreBreakRepository storeBreakRepository;
  @Autowired
  private StoreRsvDayRepository storeRsvDayRepository;
  @Autowired
  private StoreRsvItemRepository storeRsvItemRepository;
  @Autowired
  private PersonOptionRepository personOptionRepository;
  @Autowired
  private StoreBasRepository storeBasRepository;


  @Value("${base-url}") // http://localhost:8080
  private String baseUrl;
  @Value("${public.img-resource-path}") // /api/v1/public/img
  private String imgResourcePath;
  
  public ResponseEntity<ResEnt> getWorkDayList(String storeId) {
    
    // get work day
    List<String> workDayList = storeRsvDayRepository.getListOfWorkDay(storeId);

    // set return obj
    Map<String, Object> result = new HashMap<>();

    log.info("Log : Store Rsv Work Day List Success");
    result.put("list", workDayList);
    return resHandler.ok(result, HttpStatus.OK);
  }

  public ResponseEntity<ResEnt> getFullRsvDateList(RsvDtListCnt rsvDtListCnt) throws ParseException {

    List<String> disableDt = new ArrayList<>();
    
    // set break time all list
    List<String> bkTimeList = createStoreBreakTime(rsvDtListCnt);
    if(bkTimeList.size() > 0) disableDt.addAll(bkTimeList);
    
    /**
     * get reservation date on month
     * 요청한 달(month)에 예약 리스트 조회(date를 groupby해서 조회)
     */
    RsvHsBasMthSvc rsvHsBasMthSvcDto = mpr.map(rsvDtListCnt, RsvHsBasMthSvc.class);
    rsvHsBasMthSvcDto.setStatusCd(Const.STATUS_CD_Y.val);
    List<RsvHsBasMthEnt> rsvHsBasMthEnt =  rsvHsBasRepository.getListMonth(rsvHsBasMthSvcDto);

    /**
     * get date of reservation completed
     * 예약 건수 한개 이상 있는 date를 rsvTimeStatusList함수에 넣어 해당 date에 예약이 완료된지 확인
     */
    for(RsvHsBasMthEnt el : rsvHsBasMthEnt) {
      Map<String, Object> times = getRsvTimeStatusList(rsvDtListCnt.getStoreId(), el.getDayCd(), el.getRsvDt());
      if(times.get("allTimeIsNRsvYn").equals(Const.STATUS_CD_Y.val)) disableDt.add(el.getRsvDt());
    }

    /**
     * check today time
     * 오늘 날짜에 현재시간 이후의 예약 시간 여부 체크
     */
    String todayDt = checkTodayDt(rsvDtListCnt);
    if(todayDt != null) disableDt.add(todayDt);

    Map<String, Object> result = new HashMap<>();
    result.put("list", disableDt);

    log.info("Log : Store Rsv Full Date List Success");
    return resHandler.ok(result, HttpStatus.OK);

  }

  public ResponseEntity<ResEnt> getRsvTimeList(RsvTimeListCnt rsvTimeListCnt) throws ParseException {

    // get time info
    Map<String, Object> times = getRsvTimeStatusList(rsvTimeListCnt.getStoreId(), rsvTimeListCnt.getDayCd(), rsvTimeListCnt.getRsvDt());
    
    Map<String, Object> result = new HashMap<>();
    result.put("list", times.get("list"));

    log.info("Log : Store Rsv Time List Success");
    return resHandler.ok(result, HttpStatus.OK);
  }

  public ResponseEntity<ResEnt> getRsvItemList(RsvItemListCnt rsvItemListCntDto) {

    Map<String, Object> items = getRsvItemStatusList(rsvItemListCntDto.getStoreId(), rsvItemListCntDto.getRsvTms(), rsvItemListCntDto.getDayCd());

    Map<String, Object> result = new HashMap<>();
    result.put("list", items.get("list"));
    return resHandler.ok(result, HttpStatus.OK);
  }

  @Transactional
  public ResponseEntity<ResEnt> registerRsv(RsvRegisterCnt rsvRegisterCnt) {
    // register
    RsvRegisterSvc rsvRegisterSvc = mpr.map(rsvRegisterCnt, RsvRegisterSvc.class);
    rsvRegisterSvc.setStatusCd(Const.STATUS_CD_Y.val);
    rsvRegisterSvc.setRsvTms(rsvRegisterCnt.getRsvDt() + " " + rsvRegisterCnt.getRsvTm());
    rsvHsBasRepository.register(rsvRegisterSvc);
    log.info("Log : Store Rsv Register Success");
    return resHandler.ok(HttpStatus.OK);
  }

  /**
   * @TITLE get time list 요청일시의 시간 리스트(모든 시간 예약 불가능 여부 포함)
   */
  private Map<String, Object> getRsvTimeStatusList(String storeId, String dayCd, String rsvDt) throws ParseException {
    
    /**
     * get time list
     * 요청 요일의 시간 리스트 + 인원 정보 리스트 조회
     */
    StoreRsvDayTimeSvc storeRsvDayTimeSvc = new StoreRsvDayTimeSvc();
    storeRsvDayTimeSvc.setDayCd(dayCd);
    storeRsvDayTimeSvc.setStoreId(storeId);
    List<RsvTimeDayEnt> times = storeRsvTimeRepository.getListOfDay(storeRsvDayTimeSvc);

    /**
     * check today date and check today time
     * 요청 날짜가 오늘이면 현재 시간 이전은 제외(현재시간포함)
     */
    Date date = new Date();
    SimpleDateFormat sdfDt = new SimpleDateFormat("yyyy-MM-dd");

    Date todayDt= sdfDt.parse(sdfDt.format(date));
    Date reqDt = sdfDt.parse(rsvDt);
    int dtCompare = todayDt.compareTo(reqDt);

    if(dtCompare == 0 && times.size() > 0) {
      SimpleDateFormat sdfTm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date nowTm = date;

      for(RsvTimeDayEnt item : times) {
        String listTmSt = rsvDt + " " + item.getRsvTm() + ":00";
        Date listTm = sdfTm.parse(listTmSt);
        int tmCompare = nowTm.compareTo(listTm);
        // 오늘 현재 시간보다 이전 시간은 제외(현재시간 포함)
        if(tmCompare > -1) times = times.stream().filter(i -> !i.getId().equals(item.getId())).collect(Collectors.toList());
      }
    }
    log.info("filter 후 time : {}", times);

    // 시간 리스트가 없다면 아래 검증할 필요 없이 예약 불가능
    if(times.size() <=0) {
      Map<String, Object> result = new HashMap<>();
      result.put("list", times);
      result.put("allTimeIsNRsvYn", "Y");
      return result;
    }
    
    /**
     * get item list
     * 요청 요일의 아이템 리스트 조회
     */
    StoreRsvItemSvc storeRsvItemSvc = new StoreRsvItemSvc();
    storeRsvItemSvc.setStoreId(storeId);
    storeRsvItemSvc.setDayCd(dayCd);
    List<StoreRsvItemEnt> items = storeRsvItemRepository.getList(storeRsvItemSvc);

    /**
     * set init(all time reservation YN)
     * 모든 시간이 예약 불가능한 상태 초기화 설정(Y: 모든시간예약불가능, N: 모든시간예약불가능 아님)
     * 시간 리스트 개수와 예약된 시간 리스트의 개수가 동일할 경우 모든 시간은 예약 불가능한 상태로 초기화
     */
    String allTimeIsNRsvYn = "N";

    if(items.size() <= 0) {

      /**
       * 시간 하위에 등록 된 아이템이 없는 경우 시간만 검증
       * rsvDt, storeId 로 rsvHsBas에서 예약된 시간 조회(rsvTms으로 groupBy시키고 rsv_per은 sum시킨다)
       * 조회된 시간이 있다면, person_option을 조회해서 총원여부 판단
       */
      RsvHsBasSumTimeSvc rsvHsBasSumTimeSvc = new RsvHsBasSumTimeSvc();
      rsvHsBasSumTimeSvc.setRsvDt(rsvDt);
      rsvHsBasSumTimeSvc.setStoreId(storeId);
      rsvHsBasSumTimeSvc.setStatusCd(Const.STATUS_CD_Y.val);
      List<RsvHsBasSumTimeEnt> rsvTimes = rsvHsBasRepository.getListOfSumTime(rsvHsBasSumTimeSvc);

      // 시간리스트와 예약된 시간리스트의 사이즈가 동일하면 일단 모든 시간은 예약 불가능 상태로 설정
      if(times.size() <= rsvTimes.size()) allTimeIsNRsvYn = "Y";

      // 예약된 시간의 인원 옵션 조회
      PersonOptionSvc personOptionSvc = new PersonOptionSvc();
      for(RsvHsBasSumTimeEnt el : rsvTimes) {

        // get person options
        personOptionSvc.setStoreRsvTimeId(el.getStoreRsvTimeId());
        List<PersonOptionEnt> options = personOptionRepository.getList(personOptionSvc);
  
        if(options.size() > 0) {
  
          // 예약 총인원 찾기(예약 총인원 옵션이 있고 총인원만큼 예약됬는지 확인)
          for(PersonOptionEnt chEl : options) {
            if(chEl.getLimitYn() != null && chEl.getLimitYn().equals(Const.STATUS_CD_Y.val)) { // 예약 총인원 설정이 Y인 경우
              if(el.getSumRsvPer() >= chEl.getNumPerson()) {
                times.stream().filter(ob -> ob.getId().equals(el.getStoreRsvTimeId())).collect(Collectors.toList()).get(0).setRsvStatusYn(Const.STATUS_CD_N.val);
              }else{
                times.stream().filter(ob -> ob.getId().equals(el.getStoreRsvTimeId())).collect(Collectors.toList()).get(0).setRsvStatusYn(Const.STATUS_CD_Y.val);
                allTimeIsNRsvYn = "N";
              }
              break;
            }else{
              times.stream().filter(ob -> ob.getId().equals(el.getStoreRsvTimeId())).collect(Collectors.toList()).get(0).setRsvStatusYn(Const.STATUS_CD_N.val);
            }
          }
          
        } else {
          // 인원옵션이 없는 경우 한 시간당 한개의 예약만 가능
          times.stream().filter(ob -> ob.getId().equals(el.getStoreRsvTimeId())).collect(Collectors.toList()).get(0).setRsvStatusYn(Const.STATUS_CD_N.val);
        }
        
        // 현재 예약된 인원 등록
        times.stream().filter(ob -> ob.getId().equals(el.getStoreRsvTimeId())).collect(Collectors.toList()).get(0).setCurrentRsvPer(el.getSumRsvPer());
      }

    } else{

      /**
       * 시간 하위에 등록 된 아이템이 있는 경우
       */
      for(RsvTimeDayEnt el : times) {
        String rsvTms = rsvDt + " " + el.getRsvTm(); // 예약 일시
        Map<String, Object> itemRsv = getRsvItemStatusList(storeId, rsvTms, dayCd);
        
        // 모든 아이템이 예약이 불가능하면 해당 시간은 예약이 불가능한 상태로 설정
        if(itemRsv.get("allItemIsNRsvYn").equals("Y")) {
          el.setRsvStatusYn(Const.STATUS_CD_N.val);
          allTimeIsNRsvYn = "Y";
        }else{
          el.setRsvStatusYn(Const.STATUS_CD_Y.val);
          allTimeIsNRsvYn = "N";
        }
      }

    }

    Map<String, Object> result = new HashMap<>();
    result.put("list", times);
    result.put("allTimeIsNRsvYn", allTimeIsNRsvYn);
    return result;
  }

  /**
   * @TITLE get item list
   * 요청일시의 아이템 리스트(모든 아이템 예약 불가능 여부 포함)
   */
  private Map<String, Object> getRsvItemStatusList(String storeId, String rsvTms, String dayCd) {

    // return obj
    Map<String, Object> result = new HashMap<>();

    /**
     * get all item reservation mode
     * "모든 아이템 예약 모드" 정보 가져오기
     * Y경우 모든 아이템이 예약될 경우 예약 마감
     * N경우 한개 아이템이 예약되면 예약 마감
     */
    StoreOneEnt store = storeBasRepository.getOne(storeId);

    /**
     * set data for item list
     * 아이템 정보를 얻기위한 데이터 설정
     */
    StoreRsvDayItemSvc storeRsvDayItemSvc = new StoreRsvDayItemSvc();
    storeRsvDayItemSvc.setDayCd(dayCd);
    storeRsvDayItemSvc.setStoreId(storeId);
    storeRsvDayItemSvc.setBaseImgUrl(baseUrl + imgResourcePath);
    storeRsvDayItemSvc.setImgGroup(Const.FL_G_ITEM_IMG.val);

    /**
     * set data for reservation history list
     * 예약된 정보를 찾기 위한 데이터 설정
     */
    RsvHsBasSumPerSvc rsvHsBasSumPerSvc = new RsvHsBasSumPerSvc();
    rsvHsBasSumPerSvc.setStoreId(storeId);
    rsvHsBasSumPerSvc.setRsvTms(rsvTms);
    rsvHsBasSumPerSvc.setStatusCd(Const.STATUS_CD_Y.val);

    /**
     * case : all item reservation mode N
     */
    if(store.getAllItemRsvMode().equals(Const.STATUS_CD_N.val)) {
      log.info("Log : All Item Reservation Mode N ");

      List<RsvHsBasSumPerEnt> rsvItems = rsvHsBasRepository.getListOfSumItem(rsvHsBasSumPerSvc);

      // 예약 건수가 1개 이상으로 더이상 예약 불가능
      if(rsvItems.size() > 0) {
        result.put("allItemIsNRsvYn", "Y");
        return result;
      }

      // 예약이 없어 아이템 리스트 return
      List<RsvItemDayEnt> items = storeRsvItemRepository.getListOfDay(storeRsvDayItemSvc);

      result.put("list", items);
      result.put("allItemIsNRsvYn", "N");
      return result;
    }

    /**
     * get item list
     * 요청한 요일에 등록된 아이템 리스트 + 인원옵션 정보 가져오기
     * 해당 조회된 리스트가 요청한 요일의 총 아이템 리스트
     */
    List<RsvItemDayEnt> items = storeRsvItemRepository.getListOfDay(storeRsvDayItemSvc);

    if(items.size() <= 0) {
      result.put("list", items);
      return result;
    }

    /**
     * get reservation history list
     * 요청한 일시(YYYY-mm-dd hh:mm:ss)에 예약된 리스트 가져오기
     */
    List<RsvHsBasSumPerEnt> rsvItems = rsvHsBasRepository.getListOfSumItem(rsvHsBasSumPerSvc);

    /**
     * set init(all item reservation YN)
     * 모든 아이템이 예약 불가능한 상태 초기화 설정 및
     * 아이템 리스트 개수와 예약된 아이템 리스트의 개수가 같은 경우 일단 모든 아이템은 예약 불가능한 상태로 설정
     */
    String allItemIsNRsvYn = "N";
    if(items.size() <= rsvItems.size()) allItemIsNRsvYn = "Y";

    /**
     * check validation of item
     * 예약된 정보가 없다면 기본값으로 모든 아이템은 예약가능 상태
     * 예약된 정보가 있다면 총원 옵션이 있는지 확인 필요
     * 총원 옵션이 없다면 예약된 아이템들은 예약 불가능 상태로 변경
     * 총원 옵션이 있다면 검증 필요(총원이랑 예약된 인원 확인 후 예약 가능상태 여부 설정)
     */
    PersonOptionSvc personOptionSvc = new PersonOptionSvc();
    for(RsvHsBasSumPerEnt el : rsvItems) {

      // get person options
      personOptionSvc.setStoreRsvItemId(el.getStoreRsvItemId());
      List<PersonOptionEnt> options = personOptionRepository.getList(personOptionSvc);

      if(options.size() > 0) {

        // 예약 총인원 찾기(예약 총인원 옵션이 있고 총인원만큼 예약됬는지 확인)
        for(PersonOptionEnt chEl : options) {
          if(chEl.getLimitYn() != null && chEl.getLimitYn().equals(Const.STATUS_CD_Y.val)) { // 예약 총인원 설정이 Y인 경우
            if(el.getSumRsvPer() >= chEl.getNumPerson()) {
              items.stream().filter(ob -> ob.getItemId().equals(el.getItemId())).collect(Collectors.toList()).get(0).setRsvStatusYn(Const.STATUS_CD_N.val);
            }else{
              items.stream().filter(ob -> ob.getItemId().equals(el.getItemId())).collect(Collectors.toList()).get(0).setRsvStatusYn(Const.STATUS_CD_Y.val);
              allItemIsNRsvYn = "N";
            }
            break;
          }else{
            items.stream().filter(ob -> ob.getItemId().equals(el.getItemId())).collect(Collectors.toList()).get(0).setRsvStatusYn(Const.STATUS_CD_N.val);
          }
        }
        
      } else {
        // 인원옵션이 없는 경우 한 아이템당 한개의 예약만 가능
        items.stream().filter(ob -> ob.getItemId().equals(el.getItemId())).collect(Collectors.toList()).get(0).setRsvStatusYn(Const.STATUS_CD_N.val);
      }
      
      // 현재 예약된 인원 등록
      items.stream().filter(ob -> ob.getItemId().equals(el.getItemId())).collect(Collectors.toList()).get(0).setCurrentRsvPer(el.getSumRsvPer());

    }

    result.put("list", items);
    result.put("allItemIsNRsvYn", allItemIsNRsvYn);
    return result;
  }
  

  /**
   * @TITLE create store break time list
   */
  private List<String> createStoreBreakTime(RsvDtListCnt rsvDtListCnt) throws ParseException {
    
    // get store break time list
    StoreBreakDtSvc storeBreakDtSvc = mpr.map(rsvDtListCnt, StoreBreakDtSvc.class);
    List<StoreBreakDtEnt> bkTime = storeBreakRepository.getListOfMonth(storeBreakDtSvc);
    
    // set date format
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    // set break time all list
    List<String> dates = new ArrayList<>();
    for(StoreBreakDtEnt el : bkTime) {
      Date startDt = sdf.parse(el.getBkStDt());
      Date endDt = sdf.parse(el.getBkEdDt());
      Date currentDt = startDt;
      
      while (currentDt.compareTo(endDt) <= 0) {
        dates.add(sdf.format(currentDt));

        Calendar c = Calendar.getInstance();
        c.setTime(currentDt);
        c.add(Calendar.DAY_OF_MONTH, 1);
        currentDt = c.getTime();
      }
    }

    return dates;
  }

  /**
   * @TITLE check today date
   * 오늘날짜가 포함된 달일 경우 현재시간 기준으로 예약시간 체크
   */
  private String checkTodayDt(RsvDtListCnt rsvDtListCnt) throws ParseException {
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

    Date today = sdf.parse(sdf.format(date));
    Date reqDt = sdf.parse(rsvDtListCnt.getYearMth());
    
    String returnDt = null;

    if(today.compareTo(reqDt) == 0) {
      log.info("Log : 오늘날짜가 포함된 달월 검증 Start");
      
      SimpleDateFormat todayDtSdf = new SimpleDateFormat("yyyy-MM-dd");
      String todayDt = todayDtSdf.format(date);

      RsvHsBasOneSvc rsvHsBasOneSvc = new RsvHsBasOneSvc();
      rsvHsBasOneSvc.setStoreId(rsvDtListCnt.getStoreId());
      rsvHsBasOneSvc.setRsvDt(todayDt);
      List<RsvHsBasOneEnt> todayRsvHs = rsvHsBasRepository.getOne(rsvHsBasOneSvc);

      if(todayRsvHs.size() <= 0) {
        log.info("Log : 오늘날짜에 예약 히스토리가 없어 시간 검증 Start");

        Calendar cal = Calendar.getInstance();
        cal.setTime(today);         
        int dayNum = cal.get(Calendar.DAY_OF_WEEK);

        String day = null;
        switch(dayNum) {
          case 0:
              day = "SUN";  
              break ;
          case 1:
              day = "MON";
              break ;
          case 2:
              day = "TUE";
              break ;
          case 3:
              day = "WED";
              break ; 
          case 4:
              day = "THU";
              break ;
          case 5:
              day = "FIR";
              break ;
          case 6:
              day = "SAT";
              break ;
      }
      
        SimpleDateFormat todayTimeSdf = new SimpleDateFormat("HH:mm");
        String nowTime = todayTimeSdf.format(date);

        List<RsvTimeAfterNowEnt> afterNowTime = storeRsvTimeRepository.getListAfterNowTime(rsvDtListCnt.getStoreId(), day, nowTime);
        if(afterNowTime.size() <= 0) returnDt = todayDt;
      }
    }

    return returnDt;
  }

}