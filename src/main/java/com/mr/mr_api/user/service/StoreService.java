package com.mr.mr_api.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mr.mr_api.common.consts.Const;
import com.mr.mr_api.common.consts.ResCd;
import com.mr.mr_api.common.entity.ResEnt;
import com.mr.mr_api.common.handler.ResHandler;
import com.mr.mr_api.user.dto.code.CodeListSvc;
import com.mr.mr_api.user.dto.file.FileSvc;
import com.mr.mr_api.user.dto.store.StoreListCnt;
import com.mr.mr_api.user.dto.store.SubSectorListCnt;
import com.mr.mr_api.user.entity.file.FileEnt;
import com.mr.mr_api.user.entity.store.StoreListEnt;
import com.mr.mr_api.user.entity.store.StoreOneEnt;
import com.mr.mr_api.user.repository.BadgeRepository;
import com.mr.mr_api.user.repository.CodeRepository;
import com.mr.mr_api.user.repository.FileRepository;
import com.mr.mr_api.user.repository.StoreBasRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StoreService {

  @Autowired
  StoreBasRepository storeBasRepository;
  @Autowired
  BadgeRepository badgeRepository;
  @Autowired
  FileRepository fileRepository;
  @Autowired
  CodeRepository codeRepository;
  @Autowired
  ResHandler resHandler;

  @Value("${base-url}") // http://localhost:8080
  private String baseUrl;
  @Value("${public.img-resource-path}") // /api/v1/public/img
  private String imgResourcePath;

  public ResponseEntity<ResEnt> getStoreList(StoreListCnt storeListCnt) {
    // get store infos && total
    storeListCnt.setImgGroup(Const.FL_G_STO_MAIN_IMG.val);
    storeListCnt.setSectorGroup(Const.CD_G_SECTOR.val);
    storeListCnt.setSubSectorGroup(Const.CD_G_SUBSECTOR.val);
    List<StoreListEnt> listBas = storeBasRepository.getListOfInfo(storeListCnt);
    int total = storeBasRepository.getTotal(storeListCnt);

    // get img url
    for(int i = 0; i < listBas.size(); i++) {
      listBas.get(i).setImgUrl(baseUrl + imgResourcePath + "/" + listBas.get(i).getImgUrl());
    }

    // set res
    Map<String, Object> result = new HashMap<>();
    result.put("list", listBas);
    result.put("total", total);
    log.info("Log : Get Store List Success");
    return resHandler.ok(result, HttpStatus.OK);
  }
  
  public ResponseEntity<ResEnt> getStoreOne(String storeId) {
    StoreOneEnt storeOne = storeBasRepository.getOne(storeId);
    if(storeOne == null) return resHandler.err(ResCd.NOT_DATA, HttpStatus.BAD_REQUEST);

    // set res
    Map<String, Object> result = new HashMap<>();
    result.put("info", storeOne);
    log.info("Log : Get Store Bas One Success");
    return resHandler.ok(result, HttpStatus.OK);
  }

  public ResponseEntity<ResEnt> getStoreBasImgs(String storeId) {
    // set file value
    FileSvc fileSvc = new FileSvc();
    fileSvc.setRefId(storeId);
    fileSvc.setGroup(Const.FL_G_STO_BAS_IMG.val);
    List<FileEnt> fileList = fileRepository.getList(fileSvc);

    // set img url
    List<String> list = new ArrayList<>();
    for(FileEnt item : fileList) list.add(baseUrl + imgResourcePath + "/" + item.getSaveName());

    // set res
    Map<String, Object> result = new HashMap<>();
    result.put("list", list);

    log.info("Log : Get Store Bas Imgs Success");
    return resHandler.ok(result, HttpStatus.OK);
  }

  public ResponseEntity<ResEnt> getSectorList() {
    
    // set group
    CodeListSvc codeListSvc = new CodeListSvc();
    codeListSvc.setGroup(Const.CD_G_SECTOR.val);

    Map<String, Object> result = new HashMap<>();
    result.put("list", codeRepository.getList(codeListSvc));

    log.info("Log : Get Store Sector List Success");
    return resHandler.ok(result, HttpStatus.OK);
  }

  public ResponseEntity<ResEnt> getSubSectorList(SubSectorListCnt subSectorListCnt) {
    
    // set refId
    CodeListSvc codeListSvc = new CodeListSvc();
    codeListSvc.setRefId(subSectorListCnt.getId());
    codeListSvc.setGroup(Const.CD_G_SUBSECTOR.val);

    Map<String, Object> result = new HashMap<>();
    result.put("list", codeRepository.getList(codeListSvc));
    
    log.info("Log : Get Store Sub-Sector List Success");
    return resHandler.ok(result, HttpStatus.OK);
  }

}