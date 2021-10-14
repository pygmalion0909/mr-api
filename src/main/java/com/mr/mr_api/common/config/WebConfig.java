package com.mr.mr_api.common.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  
  private final ModelMapper mpr = new ModelMapper();

  /**
   * @TITLE public static resources set(img)
   * TODO 경로를 환경변수에 담기
   */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/v1/static/stores/img/**") // 해당 경로면
            .addResourceLocations("file:///C:/reservation/img/store/"); // 이쪽 경로로 바라본다.
  }

  /**
   * @TITLE model mapper set
   */
  @Bean
  public ModelMapper setModelMapperConfig() {
    mpr.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    return mpr;
  }

}