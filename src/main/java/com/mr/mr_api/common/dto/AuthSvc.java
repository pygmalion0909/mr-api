package com.mr.mr_api.common.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthSvc extends User{
	
	private String id;
	private String roleCd;
	
	public AuthSvc(String username, String password, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
	}
  
}