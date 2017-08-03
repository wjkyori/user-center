package org.transformer.user.shiro.pac4j;

import com.google.common.collect.Lists;

import io.buji.pac4j.filter.CallbackFilter;
import io.buji.pac4j.filter.SecurityFilter;
import io.buji.pac4j.realm.Pac4jRealm;
import io.buji.pac4j.subject.Pac4jSubjectFactory;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.pac4j.cas.client.CasClient;
import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.cas.config.CasProtocol;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.core.context.J2EContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

/**
 * spring shiro pac4j-cas 集成配置.
 */
@Configuration
public class ShiroWithPac4jCasConfiguration {

  // CAS服务器地址
  private String casServerUrlPrefix = "http://127.0.0.1:8081/cas";

  // Cas登录页面地址
  private String casLoginUrl = casServerUrlPrefix + "/login";

  // Cas登出页面地址
  private String casLogoutUrl = casServerUrlPrefix + "/logout";

  // 当前工程对外提供的服务地址
  private String serverUrl = "http://127.0.0.1:8080/api";

  //api 登录验证地址  
  //private String apiLoginUrl = "/api/login";

  //web 页面登录验证地址
  private String webLoginUrl = "/login";

  //api请求地址
  // private String apiUrlPattern = "/api/**";

  //web请求地址
  private String webUrlPattern = "/**";

  //Access-Token 字符串.
  private String accessTokenName = "access-token";

  //web登录
  private static final String webLogin = "webLogin";

  //web请求验证
  private static final String webSecurity = "webSecurity";

  //api登录
  //  private static final String apiLogin = "apiLogin";

  //api请求验证
  //  private static final String apiSecurity = "apiSecurity";

  @Bean(name = "lifecycleBeanPostProcessor")
  public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
    return new LifecycleBeanPostProcessor();
  }

  /**
   * 安全验证管理器.
   * @return.
   */
  @Bean(name = "securityManager")
  public DefaultWebSecurityManager getDefaultWebSecurityManager(
      DefaultWebSessionManager sessionManager) {
    DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
    dwsm.setRealm(new Pac4jRealm());
    // 指定 SubjectFactory
    dwsm.setSubjectFactory(new Pac4jSubjectFactory());
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
    Cookie sessionIdCookie = new SimpleCookie(accessTokenName);
    sessionManager.setSessionIdCookie(sessionIdCookie);
    return sessionManager;
  }

  /**
   * 配置相应的请求走相应的过滤器.
   * @param shiroFilterFactoryBean.
   */
  private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean) {
    /////////////////////// 下面这些规则配置最好配置到配置文件中 ///////////////////////
    Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
    filterChainDefinitionMap.put(webLoginUrl, webLogin);
    //filterChainDefinitionMap.put(apiLoginUrl, apiLogin);
    //filterChainDefinitionMap.put("/api/logout", "logout");
    filterChainDefinitionMap.put("/logout", "logout");
    //filterChainDefinitionMap.put(apiUrlPattern, apiSecurity);
    filterChainDefinitionMap.put(webUrlPattern, webSecurity);

    shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
  }

  /**
   * ShiroFilter.
   */
  @Bean(name = "shiroFilter")
  public ShiroFilterFactoryBean getShiroFilterFactoryBean(
      DefaultWebSecurityManager securityManager) {
    ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
    // 必须设置 SecurityManager  
    shiroFilterFactoryBean.setSecurityManager(securityManager);
    // 添加相关的配置到filter到shiroFilter中
    Map<String, Filter> filters = new HashMap<>();
    filters.put(webLogin, getWebLoginFilter());
    //    filters.put(apiLogin, getApiLoginFilter());
    //    filters.put(apiSecurity, getApiSecurityFilter());
    filters.put("logout", getLogoutFilter());
    filters.put(webSecurity, getWebSecurityFilter());
    shiroFilterFactoryBean.setFilters(filters);
    loadShiroFilterChain(shiroFilterFactoryBean);
    return shiroFilterFactoryBean;
  }

  /**
   * 页面请求登录验证过滤器.
   * @return .
   */
  private Filter getWebLoginFilter() {
    CallbackFilter webLoginFilter = new CallbackFilter();
    webLoginFilter.setCallbackLogic(getCallbackLogic());
    webLoginFilter.setConfig(getWebConfig());
    webLoginFilter.setDefaultUrl(serverUrl);
    return webLoginFilter;
  }

  /**
   * 页面请求权限验证过滤器.
   * @return .
   */
  private Filter getWebSecurityFilter() {
    SecurityFilter webSecurityFilter = new SecurityFilter();
    webSecurityFilter.setConfig(getWebConfig());
    webSecurityFilter.setClients(webSecurity);
    webSecurityFilter.setSecurityLogic(getSecurityLogic());
    return webSecurityFilter;
  }

  /**
   * 登出
   * @return.
   */
  public Filter getLogoutFilter() {
    SecurityFilter webSecurityFilter = new SecurityFilter();
    webSecurityFilter.setConfig(getWebConfig());
    webSecurityFilter.setClients(webSecurity);
    webSecurityFilter.setSecurityLogic(getLogoutLogic());
    return webSecurityFilter;
  }

  //  /**
  //   * API登录验证过滤器.
  //   * @return .
  //   */
  //  private Filter getApiLoginFilter() {
  //    CallbackFilter apiLoginFilter = new CallbackFilter();
  //    apiLoginFilter.setConfig(geApiConfig());
  //    apiLoginFilter.setCallbackLogic(getCallbackLogic());
  //    return apiLoginFilter;
  //  }
  //
  //  /**
  //   * API权限验证过滤器
  //   * @return .
  //   */
  //  private Filter getApiSecurityFilter() {
  //    SecurityFilter apiSecurityFilter = new SecurityFilter();
  //    apiSecurityFilter.setConfig(geApiConfig());
  //    apiSecurityFilter.setClients(apiSecurity);
  //    apiSecurityFilter.setSecurityLogic(getSecurityFilter());
  //    return apiSecurityFilter;
  //  }

  /**
   * API请求处理配置.
   * @return.
   */
  //  private Config geApiConfig() {
  //    CasConfiguration casConfiguration = new CasConfiguration(casLoginUrl, casServerUrlPrefix);
  //    casConfiguration.setProtocol(CasProtocol.CAS20);
  //    //    casConfiguration.setCallbackUrlResolver((callbackUrl, context) -> {
  //    //      if (callbackUrl.equals(serverUrl + apiLoginUrl)) {
  //    //        //如果是登录请求且是ajax请求，转化成真实的登录地址
  //    //        if (new DefaultAjaxRequestResolver().isAjax(context)) {
  //    //          String referer = context.getRequestHeader(HttpHeaders.REFERER);
  //    //          referer = CommonHelper.substringBefore(referer,
  //    //              "?" + CasConfiguration.TICKET_PARAMETER + "=");
  //    //          referer = CommonHelper.substringBefore(referer,
  //    //              "&" + CasConfiguration.TICKET_PARAMETER + "=");
  //    //          return referer;
  //    //        }
  //    //      }
  //    //      return callbackUrl;
  //    //    });
  //    CasClient casClient = new CasClient();
  //    casClient.setCallbackUrl(serverUrl + apiLoginUrl);
  //    casClient.setConfiguration(casConfiguration);
  //    casClient.setIncludeClientNameInCallbackUrl(false);
  //    casClient.setName(apiSecurity);
  //    Clients clients = new Clients(Lists.newArrayList());
  //    clients.getClients().add(casClient);
  //    clients.setDefaultClient(casClient);
  //    Config config = new Config();
  //    config.setClients(clients);
  //    return config;
  //  }

  /**
   * 获取请求处理客户端.
   * @return.
   */
  private Config getWebConfig() {
    CasConfiguration casConfiguration = new CasConfiguration(casLoginUrl, casServerUrlPrefix);
    casConfiguration.setProtocol(CasProtocol.CAS20);
    CasClient casClient = new CasClient();
    casClient.setCallbackUrl(serverUrl + webLoginUrl);
    casClient.setConfiguration(casConfiguration);
    casClient.setIncludeClientNameInCallbackUrl(false);
    casClient.setName(webSecurity);
    Clients clients = new Clients(Lists.newArrayList());
    clients.getClients().add(casClient);
    clients.setDefaultClient(casClient);
    Config config = new Config();
    config.setClients(clients);
    return config;
  }

  /** 登录回调请求处理器. */
  private CallbackLogic<Object, J2EContext> getCallbackLogic() {
    CallbackLogic<Object, J2EContext> callbackLogic = new CallbackLogic<>();
    callbackLogic.setAccessTokenName(accessTokenName);
    return callbackLogic;
  }

  /** 请求验证处理器. */
  private SecurityLogic<Object, J2EContext> getSecurityLogic() {
    SecurityLogic<Object, J2EContext> securityLogic = new SecurityLogic<>();
    return securityLogic;
  }

  /** 退出处理器. */
  private LogoutLogic<Object, J2EContext> getLogoutLogic() {
    LogoutLogic<Object, J2EContext> logoutLogic = new LogoutLogic<>();
    logoutLogic.setCasServerLogoutUrl(casLogoutUrl);
    logoutLogic.setRedirectUrl(serverUrl);
    return logoutLogic;
  }
}
