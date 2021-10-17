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
import com.mr.mr_api.user.dto.store.StoreListCnt;
import com.mr.mr_api.user.dto.store.StoreOneDto;
import com.mr.mr_api.user.dto.store.SubSectorListCnt;
import com.mr.mr_api.user.entity.store.StoreListEnt;
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
    List<StoreListEnt> listBas = storeBasRepository.getListOfInfo(storeListCnt);
    int total = storeBasRepository.getTotal(storeListCnt);

    // get badge infos
    for(int i = 0; i < listBas.size(); i++) {
      listBas.get(i).setImgUrl(baseUrl + imgResourcePath + "/" + listBas.get(i).getImgUrl());
      // TODO dto이름 수정
      listBas.get(i).setBadgeList(badgeRepository.getListOfInfo(listBas.get(i).getId()));
    }

    // set res
    Map<String, Object> result = new HashMap<>();
    result.put("list", listBas);
    result.put("total", total);
    return resHandler.ok(result, HttpStatus.OK);
  }
  
  public ResponseEntity<ResEnt> getStoreOne(String storeId) {
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
    fileDto.setGroup(Const.FL_G_STO_BAS_IMG.val);

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
    codeListSvc.setGroup(Const.CD_G_SECTOR.val);

    Map<String, Object> result = new HashMap<>();
    result.put("list", codeRepository.getList(codeListSvc));
    return resHandler.ok(result, HttpStatus.OK);
  }

  public ResponseEntity<ResEnt> getSubSectorList(SubSectorListCnt subSectorListCnt) {
    
    // set refId
    CodeListSvc codeListSvc = new CodeListSvc();
    codeListSvc.setRefId(subSectorListCnt.getId());
    codeListSvc.setGroup(Const.CD_G_SUBSECTOR.val);

    Map<String, Object> result = new HashMap<>();
    result.put("list", codeRepository.getList(codeListSvc));
    return resHandler.ok(result, HttpStatus.OK);
  }

}