package org.transformer.user.shiro.cas;

import org.apache.shiro.cas.CasFilter;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.cas.CasSubjectFactory;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.DelegatingFilterProxy;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

/**
  * spring 继承shiro-cas配置.
  */
//@Configuration
@Deprecated
public class ShiroWithShiroCasConfiguration {

  // CasServerUrlPrefix
  public static final String casServerUrlPrefix = "https://cas.linesum.com";
  // Cas登录页面地址
  public static final String casLoginUrl = casServerUrlPrefix + "/login";
  // Cas登出页面地址
  public static final String casLogoutUrl = casServerUrlPrefix + "/logout";
  // 当前工程对外提供的服务地址
  public static final String shiroServerUrlPrefix = "http://localhost:4000";
  // casFilter UrlPattern
  public static final String casFilterUrlPattern = "/login";
  // 登录地址
  public static final String loginUrl = casLoginUrl + "?service=" + shiroServerUrlPrefix
      + casFilterUrlPattern;

  /**
   * realm.
   * @return.
   */
  @Bean(name = "casRealm")
  public CasRealm casRealm() {
    CasRealm realm = new CasRealm();
    realm.setCasServerUrlPrefix(casServerUrlPrefix);
    realm.setCasService(shiroServerUrlPrefix + casFilterUrlPattern);
    return realm;
  }

  @Bean(name = "ajaxFormAuthenticationFilter")
  public AjaxFormAuthenticationFilter ajaxFormAuthenticationFilter() {
    return new AjaxFormAuthenticationFilter();
  }

  /**
  * 注册DelegatingFilterProxy（Shiro）.
  */

  @Bean
  public FilterRegistrationBean filterRegistrationBean() {
    FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
    filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
    //  该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理  
    filterRegistration.addInitParameter("targetFilterLifecycle", "true");
    filterRegistration.setEnabled(true);
    return filterRegistration;
  }

  @Bean(name = "lifecycleBeanPostProcessor")
  public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
    return new LifecycleBeanPostProcessor();
  }

  /**
   * session管理器.
   * @param sessionManager.
   * @param casRealm.
   * @return.
   */
  @Bean(name = "securityManager")
  public DefaultWebSecurityManager getDefaultWebSecurityManager(
      DefaultWebSessionManager sessionManager, CasRealm casRealm) {
    DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
    dwsm.setRealm(casRealm);
    //      <!-- 用户授权/认证信息Cache, 采用EhCache 缓存 -->
    // 指定 SubjectFactory
    dwsm.setSubjectFactory(new CasSubjectFactory());
    dwsm.setSessionManager(sessionManager);
    return dwsm;
  }

  /**
   * session管理器.
   * @return.
   */
  @Bean(name = "sessionManager")
  public DefaultWebSessionManager defaultWebSessionManager() {
    DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
    sessionManager.setGlobalSessionTimeout(7200000);
    Cookie sessionIdCookie = new SimpleCookie("Access-Token");
    sessionManager.setSessionIdCookie(sessionIdCookie);
    return sessionManager;
  }

  /**
  * 加载shiroFilter权限控制规则.  
  * 
  */
  private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean) {
    /////////////////////// 下面这些规则配置最好配置到配置文件中 ///////////////////////
    Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
    filterChainDefinitionMap.put(casFilterUrlPattern, "casFilter");
    filterChainDefinitionMap.put("/notLogin", "anon");
    filterChainDefinitionMap.put("/**", "authc");
    shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
  }

  /**
  * CAS登录过滤器.
  *
  */
  @Bean(name = "casFilter")
  public CasFilter getCasFilter() {
    CasFilter casFilter = new AjaxCasFilter();
    casFilter.setName("casFilter");
    casFilter.setEnabled(true);
    // 登录失败后跳转的URL，也就是 Shiro 执行 CasRealm 的 doGetAuthenticationInfo 方法向CasServer验证tiket
    casFilter.setFailureUrl(loginUrl);// 我们选择认证失败后再打开登录页面
    return casFilter;
  }

  /**
    * ShiroFilter.
    */
  @Bean(name = "shiroFilter")
  public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager,
      CasFilter casFilter, AjaxFormAuthenticationFilter ajaxFormAuthenticationFilter) {
    ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
    // 必须设置 SecurityManager  
    shiroFilterFactoryBean.setSecurityManager(securityManager);
    // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
    shiroFilterFactoryBean.setLoginUrl(loginUrl);
    // 登录成功后要跳转的连接
    shiroFilterFactoryBean.setSuccessUrl("/");
    // 添加casFilter到shiroFilter中
    Map<String, Filter> filters = new HashMap<>();
    filters.put("casFilter", casFilter);
    filters.put("authc", ajaxFormAuthenticationFilter);
    shiroFilterFactoryBean.setFilters(filters);
    loadShiroFilterChain(shiroFilterFactoryBean);
    return shiroFilterFactoryBean;
  }

}
