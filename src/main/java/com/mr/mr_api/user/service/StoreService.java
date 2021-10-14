package com.mr.mr_api.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mr.mr_api.common.consts.Const;
import com.mr.mr_api.common.consts.ResCd;
import com.mr.mr_api.common.entity.ResEnt;
import com.mr.mr_api.common.handler.ResHandler;
import com.mr.mr_api.user.dto.code.CodeListSvc;
import com.mr.mr_api.user.dto.file.FileDto;
import com.mr.mr_api.user.dto.store.StoreBasDto;
import com.mr.mr_api.user.dto.store.StoreListDto;
import com.mr.mr_api.user.dto.store.StoreOneDto;
import com.mr.mr_api.user.dto.store.SubSectorListCnt;
import com.mr.mr_api.user.repository.BadgeRepository;
import com.mr.mr_api.user.repository.CodeRepository;
import com.mr.mr_api.user.repository.FileRepository;
import com.mr.mr_api.user.repository.StoreBasRepository;

import org.springframework.beans.factory.annotation.Autowired;
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

  public ResponseEntity<ResEnt> getList(StoreBasDto storeBasDto) {
    // get store infos && total
    List<StoreListDto> listBas = storeBasRepository.getListOfInfo(storeBasDto);
    int total = storeBasRepository.getTotal();

    // get badge infos
    // TODO 주소 환경변수
    for(int i = 0; i < listBas.size(); i++) {
      listBas.get(i).setImgUrl("http://localhost:8080/static/img/store/" + listBas.get(i).getImgUrl());
      listBas.get(i).setBadgeList(badgeRepository.getListOfInfo(listBas.get(i).getId()));
    }

    // set res
    Map<String, Object> result = new HashMap<>();
    result.put("list", listBas);
    result.put("total", total);
    return resHandler.ok(result, HttpStatus.OK);
  }
  
  public ResponseEntity<ResEnt> getOne(String storeId) {
    StoreOneDto storeOne = storeBasRepository.getOne(storeId);
    if(storeOne == null) return resHandler.err(ResCd.NOT_DATA, HttpStatus.BAD_REQUEST);

    // set res
    Map<String, Object> result = new HashMap<>();
    result.put("info", storeOne);
    return resHandler.ok(result, HttpStatus.OK);
  }

  public ResponseEntity<ResEnt> getStoreDetailImg(String storeId) {
    // set file value
    FileDto fileDto = new FileDto();
    fileDto.setRefId(storeId);
    fileDto.setGroup(Const.GROUP_STO_BAS_IMG.val);

    List<FileDto> fileList = fileRepository.getList(fileDto);
    
    // set img url
    // TODO url주소 환경변수
    for(FileDto item : fileList) {
      item.setUrl("http://localhost8080/static/img/store" + item.getSaveName());
    }

    // set res
    Map<String, Object> result = new HashMap<>();
    result.put("list", fileList);
    return resHandler.ok(result, HttpStatus.OK);
  }

  public ResponseEntity<ResEnt> getSectorList() {
    
    // set group
    CodeListSvc codeListSvc = new CodeListSvc();
    codeListSvc.setGroup(Const.GROUP_SECTOR.val);

    Map<String, Object> result = new HashMap<>();
    result.put("list", codeRepository.getList(codeListSvc));
    return resHandler.ok(result, HttpStatus.OK);
  }

  public ResponseEntity<ResEnt> getSubSectorList(SubSectorListCnt subSectorListCnt) {
    
    // set refId
    CodeListSvc codeListSvc = new CodeListSvc();
    codeListSvc.setRefId(subSectorListCnt.getId());
    codeListSvc.setGroup(Const.GROUP_SUBSECTOR.val);

    Map<String, Object> result = new HashMap<>();
    result.put("list", codeRepository.getList(codeListSvc));
    return resHandler.ok(result, HttpStatus.OK);
  }

}