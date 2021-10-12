package com.mr.mr_api.common.batch;

import com.mr.mr_api.common.consts.Const;
import com.mr.mr_api.user.repository.MemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ScheduleUser {

  @Autowired
  MemberRepository memberRepository;

  @Scheduled(cron = "59 59 23 * * *")
  public void deleteStatusCdAUser () {
    memberRepository.deleteByStatusCd(Const.STATUS_CD_A.val, Const.ROLE_USER.val);
    log.info("Log : User of Status A Delete Successful");
  }

}