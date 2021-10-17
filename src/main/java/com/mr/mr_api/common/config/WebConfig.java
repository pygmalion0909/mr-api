package com.mr.mr_api.common.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  
  private final ModelMapper mpr = new ModelMapper();

  @Value("${public.img-resource-path}") // /api/v1/public/img
  private String imgResourcePath;
  @Value("${public.img-location-path}") // file:///C:/reservation/img
  private String imgLocationPath;

  /**
   * @TITLE public static resources set(img)
   */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler(imgResourcePath + "/**") // 해당 경로 요청하면
            .addResourceLocations(imgLocationPath + "/"); // 이쪽 경로로 바라본다.
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