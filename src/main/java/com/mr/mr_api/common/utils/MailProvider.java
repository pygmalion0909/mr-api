package com.mr.mr_api.common.utils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.mr.mr_api.common.dto.MailSendSvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MailProvider {

  @Autowired
  private JavaMailSender javaMailSender;
  @Autowired
  private SpringTemplateEngine templateEngine;

  @Value("${spring.mail.username}")
  private String fromAddr;

  /**
   * @TITLE 메일 전송
   */
  @Async
  public void send(MailSendSvc mailSendSvc) throws MessagingException {

    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper mailSet = new MimeMessageHelper(mimeMessage, "utf-8");

    // set bas email info
    mailSet.setSubject(mailSendSvc.getSubject());
    mailSet.setTo(mailSendSvc.getToAddr());
    mailSet.setFrom(fromAddr);
    // set html and text
    mailSet.setText(createHtml(mailSendSvc), true);

    log.info("Log : Mail Send Success");
    javaMailSender.send(mimeMessage);
  }

  /**
   * @TITLE 메일 본문 문서 생성
   */
  private String createHtml(MailSendSvc mailSendSvc) {
    Context context = new Context();
    context.setVariable("authKey", mailSendSvc.getAuthKey()); // 회원가입, 비밀번호찾기 사용
    context.setVariable("nickName", mailSendSvc.getNickName()); // 회원가입, 비밀번호찾기, 아이디찾기 사용
    context.setVariable("loginId", mailSendSvc.getLoginId()); // 아이디찾기 사용

    log.info("Log : Mail Templates Create Success");
    return templateEngine.process(mailSendSvc.getDocName(), context);
  }

}