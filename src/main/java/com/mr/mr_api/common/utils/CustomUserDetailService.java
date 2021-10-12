package com.mr.mr_api.common.utils;

import java.util.ArrayList;
import java.util.List;

import com.mr.mr_api.common.consts.Const;
import com.mr.mr_api.common.consts.ResCd;
import com.mr.mr_api.common.dto.AuthSvc;
import com.mr.mr_api.user.dto.user.UserSecretSvc;
import com.mr.mr_api.user.entity.user.UserSecretEnt;
import com.mr.mr_api.user.repository.MemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
  
  @Autowired
  private MemberRepository memberRepository;
  
  /**
   * @TITLE set member info obj
   * token에 담긴 loginId로 member정보를 조회하고 UsernamePasswordAuthenticationToken에 담길 객체 셋팅
   * 
   * TODO security filter중복 등록으로 인한 해당 메소드 이중 실행 중
   */
  @Override
  public AuthSvc loadUserByUsername(String loginId) {
    
    // get member info
    UserSecretSvc userSecretSvc = new UserSecretSvc();
    userSecretSvc.setLoginId(loginId);
    userSecretSvc.setStatusCd(Const.STATUS_CD_Y.val);

    // check member
    UserSecretEnt member = memberRepository.getOneOfSecret(userSecretSvc);
    if(member == null) throw new UsernameNotFoundException(ResCd.NOT_MEMBER.msg);
    
    // user role
    List<GrantedAuthority> roles = new ArrayList<>();
    roles.add(new SimpleGrantedAuthority(member.getRoleCd()));
    
    // save successful login user information
    AuthSvc authSvc = new AuthSvc(member.getLoginId(), member.getPasswd(), roles);
    authSvc.setId(member.getId());
    authSvc.setRoleCd(member.getRoleCd());
    
    return authSvc;
  }

}