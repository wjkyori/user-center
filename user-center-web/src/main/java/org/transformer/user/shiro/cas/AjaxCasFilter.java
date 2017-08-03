package org.transformer.user.shiro.cas;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.cas.CasFilter;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Deprecated
public class AjaxCasFilter extends CasFilter {

  @Override
  protected boolean isEnabled(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    if (((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.toString())) {
      return false;
    }
    return isEnabled();
  }

  @Override
  protected boolean onLoginSuccess(AuthenticationToken token, Subject subject,
      ServletRequest request, ServletResponse response) throws Exception {
    if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
      HttpServletRequest httpServletRequest = (HttpServletRequest) request;
      String header = httpServletRequest.getHeader("X-Requested-With");
      if ("XMLHttpRequest".equalsIgnoreCase(header)) {
        //AJAX请求，登录成功直接返回
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String sessionId = (String) subject.getSession().getId();
        try (PrintWriter out = httpServletResponse.getWriter()) {
          out.println("{\"access-token\":\"" + sessionId + "\"}");
          out.flush();
        }
        return false;
      }
    }
    return super.onLoginSuccess(token, subject, request, response);
  }

  /**
   * 登录失败时处理.
   */
  @Override
  protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException ae,
      ServletRequest request, ServletResponse response) {
    if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
      String header = ((HttpServletRequest) request).getHeader("X-Requested-With");
      if ("XMLHttpRequest".equalsIgnoreCase(header)) {
        //AJAX请求，未登录时抛出401，页面处理
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        return false;
      }
    }
    return super.onLoginFailure(token, ae, request, response);
  }

}
