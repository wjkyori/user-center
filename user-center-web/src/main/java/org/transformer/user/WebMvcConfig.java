package org.transformer.user;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    //    registry.addMapping("/**").allowedOrigins("http://localhost:4000").allowedMethods("*")
    //        .allowCredentials(true);
  }

  /**
   * 添加拦截器.
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new RestApiHandlerInterceptor()).addPathPatterns("/**");
  }

  /**
   * 跨域请求过滤器
   * @return.
   */
  @Bean("crosFilter")
  public FilterRegistrationBean getCrosFilter() {
    FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
    filterRegistration.setFilter(new CrosFilter());
    //  该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理  
    filterRegistration.setEnabled(true);
    //优先级最高
    filterRegistration.setOrder(1);
    filterRegistration.addUrlPatterns("/*");
    return filterRegistration;
  }
}
