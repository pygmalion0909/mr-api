package com.mr.mr_api.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

/**
 * @TITLE AuthenticationPrincipal어노테이션 커스텀
 * 접속한 member정보를 얻기 위해 어노테이션 커스텀함.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@AuthenticationPrincipal
public @interface AuthInfo {
}