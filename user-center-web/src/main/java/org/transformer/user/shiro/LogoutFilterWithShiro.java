package org.transformer.user.shiro;

import org.apache.shiro.SecurityUtils;
import org.jasig.cas.client.util.AbstractConfigurationFilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutFilterWithShiro extends AbstractConfigurationFilter {

  /**跳转地址参数.*/
  private static final String REDIRECT_URL = "redirectUrl";

  /**cas退出地址. */
  private String casServerLogoutUrl;

  /**退出后跳转地址.*/
  private String redirectUrl;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // nothing to do
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    //退出
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    /*String uri = httpRequest.getRequestURI();
    //第二次退出
    if (uri.endsWith("/logoutConfirm")) {
      //判断请求来源
      String referer = httpRequest.getHeader("referer");
      //如果来自本网站的redirect跳转，则执行退出
      if (StringUtils.isNotBlank(referer)) {
        //判断是否已经被退出
        if (SecurityUtils.getSubject().isAuthenticated()) {
          SecurityUtils.getSubject().logout();
          ((HttpServletResponse) response).sendRedirect(getLogoutUrl(httpRequest));
        } else {
          ((HttpServletResponse) response).sendRedirect(httpRequest.getContextPath() + indexUrl);
        }
      } else {
        //如果是从cas跳转回来，则跳转到主页
        ((HttpServletResponse) response).sendRedirect(httpRequest.getContextPath() + indexUrl);
      }
    } else {
      //第一次退出
      if (SecurityUtils.getSubject().isAuthenticated()) {
        SecurityUtils.getSubject().logout();
        ((HttpServletResponse) response).sendRedirect(getLogoutUrl(httpRequest));
      } else {
        //跳转到第二次退出，为了解决，第一次退出时，shiro已退出，但是CAS的TCT仍然存在，导致退出失败的问题
        ((HttpServletResponse) response)
            .sendRedirect(httpRequest.getContextPath() + "/logoutConfirm");
      }
    }*/
    SecurityUtils.getSubject().logout();
    ((HttpServletResponse) response).sendRedirect(getLogoutUrl(httpRequest));
  }

  private String getLogoutUrl(HttpServletRequest request) {
    String logout = casServerLogoutUrl;
    String redirectUrl = request.getParameter(REDIRECT_URL);
    if (redirectUrl == null) {
      redirectUrl = getRedirectUrl();
    }
    if (redirectUrl != null && !redirectUrl.trim().equals("")) {
      logout += ("?service=" + redirectUrl);
    }
    return logout;
  }

  @Override
  public void destroy() {
    // nothing to do
  }

  /**
  * 获取cas退出地址 .
  * @return casServerLogoutUrl cas退出地址
  */
  public String getCasServerLogoutUrl() {
    return casServerLogoutUrl;
  }

  /**
  * 设置cas退出地址 .
  * @param casServerLogoutUrl cas退出地址
  */
  public void setCasServerLogoutUrl(String casServerLogoutUrl) {
    this.casServerLogoutUrl = casServerLogoutUrl;
  }

  /**
  * 获取退出后跳转地址 .
  * @return redirectUrl 退出后跳转地址
  */
  public String getRedirectUrl() {
    return redirectUrl;
  }

  /**
  * 设置退出后跳转地址 .
  * @param redirectUrl 退出后跳转地址
  */
  public void setRedirectUrl(String redirectUrl) {
    this.redirectUrl = redirectUrl;
  }
}
