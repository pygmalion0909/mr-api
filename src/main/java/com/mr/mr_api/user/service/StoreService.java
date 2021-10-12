package com.mr.mr_api.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mr.mr_api.common.consts.Const;
import com.mr.mr_api.common.consts.ResCd;
import com.mr.mr_api.common.entity.ResEnt;
import com.mr.mr_api.common.handler.ResHandler;
import com.mr.mr_api.user.dto.file.FileDto;
import com.mr.mr_api.user.dto.store.StoreBasDto;
import com.mr.mr_api.user.dto.store.StoreListDto;
import com.mr.mr_api.user.dto.store.StoreOneDto;
import com.mr.mr_api.user.repository.BadgeRepository;
import com.mr.mr_api.user.repository.FileRepository;
import com.mr.mr_api.user.repository.StoreBasRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StoreService {
  
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired
  StoreBasRepository storeBasRepository;
  @Autowired
  BadgeRepository badgeRepository;
  @Autowired
  FileRepository fileRepository;
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
    fileDto.setGroup(Const.ST_BAS_IMG_GU.val);

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

}