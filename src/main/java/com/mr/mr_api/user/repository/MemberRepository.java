package com.mr.mr_api.user.repository;

import com.mr.mr_api.user.dto.user.UserOneSvc;
import com.mr.mr_api.user.dto.user.UserSecretSvc;
import com.mr.mr_api.user.dto.user.ReqAuthPasswdSvc;
import com.mr.mr_api.user.dto.user.SignupSvc;
import com.mr.mr_api.user.dto.user.UpdatePasswdSvc;
import com.mr.mr_api.user.entity.user.UserOneEnt;
import com.mr.mr_api.user.entity.user.UserSecretEnt;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberRepository {

  public UserOneEnt getOne(UserOneSvc userOneSvc);
  public UserSecretEnt getOneOfSecret(UserSecretSvc userSecretSvc);
  public int getCountOfDupl(UserOneSvc userOneSvc);
  public void signup(SignupSvc signupSvc);
  public void updateSignupAuth(@Param("loginId") String loginId, @Param("statusCd") String statusCd);
  public void updateReqAuthPasswd(ReqAuthPasswdSvc reqAuthPasswdSvc);
  public void updatePasswd(UpdatePasswdSvc updatePasswdSvc);
  public void deleteByStatusCd(@Param("statusCd") String statusCd, @Param("roleCd") String roleCd);

}