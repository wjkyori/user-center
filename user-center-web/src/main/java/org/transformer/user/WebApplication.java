package org.transformer.user;

import io.buji.pac4j.subject.Pac4jPrincipal;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.transformer.user.web.dto.manage.account.UserInfo;

@SpringBootApplication
@Controller
@ComponentScan(basePackages = { "org.transformer" })
public class WebApplication extends SpringBootServletInitializer {

  /** 主页. */
  @RequestMapping("/")
  @ResponseBody
  public String index() {
    return "Hello world ！";
  }

  /**
   * 获取当前用户信息.
   * @return userInfo
   */
  @RequestMapping("/user")
  @ResponseBody
  public UserInfo user() {
    Subject subject = SecurityUtils.getSubject();
    if (null != subject) {
      // 创建新对象
      String userName = ((Pac4jPrincipal) subject.getPrincipal()).getName();
      UserInfo user = new UserInfo();
      user.setUserName(userName);
      return user;
    }
    return null;
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(WebApplication.class);
  }

  public static void main(String[] args) {
    SpringApplication.run(WebApplication.class, args);
  }
}