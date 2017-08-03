package org.transformer.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ResultApi拦截器.
 */
public class RestApiHandlerInterceptor extends HandlerInterceptorAdapter {

  /**
   * 处理完后的拦截器.
   * 修改出完的后成功的状态吗
   * POST,PUT,PATCH返回201
   * DELETE 返回204
   */
  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {
    if (response.getStatus() == HttpStatus.OK.value()) {
      if (request.getMethod().equals(RequestMethod.POST.name())
          || request.getMethod().equals(RequestMethod.PUT.name())
          || request.getMethod().equals(RequestMethod.PATCH.name())) {
        response.setStatus(HttpStatus.CREATED.value());
      } else if (request.getMethod().equals(RequestMethod.DELETE.name())) {
        response.setStatus(HttpStatus.NO_CONTENT.value());
      }
    }
  }
}
