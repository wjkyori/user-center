package org.transformer.user.shiro.cas;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AjaxFormAuthenticationFilter extends FormAuthenticationFilter {

  @Override
  protected boolean isEnabled(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    if (((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.toString())) {
      return false;
    }
    return isEnabled();
  }

  @Override
  protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
      throws Exception {
    if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
      String header = ((HttpServletRequest) request).getHeader("X-Requested-With");
      if ("XMLHttpRequest".equalsIgnoreCase(header)) {
        //AJAX请求，未登录时抛出401，页面处理
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        return false;
      }
    }
    return super.onAccessDenied(request, response);

  }
}
