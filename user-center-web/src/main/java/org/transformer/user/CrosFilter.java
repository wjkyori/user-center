package org.transformer.user;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebFilter
public class CrosFilter extends OncePerRequestFilter {

  /**
   * 跨域请求头.
   */
  private String allowHeaders = "Accept, Origin, X-Requested-With, Content-Type, LastModified";


  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
    if (!isSameOrigin(request)) {
      //跨域请求,设置跨域信息
      response.setHeader("Access-Control-Allow-Methods", "GET,POST,PATCH,DELETE,PUT,OPTIONS");
      response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:4000");
      response.setHeader("Access-Control-Allow-Credentials", "true");
      response.setHeader("Access-Control-Max-Age", "7200");
      response.setHeader("Access-Control-Allow-Headers",
          allowHeaders);
      response.setHeader("Access-Control-Expose-Headers", "Location");
    }
    if (!request.getMethod().equals(RequestMethod.OPTIONS.toString())) {
      //如果只是 options请求，无需继续处理
      filterChain.doFilter(request, response);
    }
  }

  /**
   * 判断是否同域请求.
   *
   * @param request .
   * @return .
   */
  private boolean isSameOrigin(HttpServletRequest request) {
    String orgin = request.getHeader(HttpHeaders.ORIGIN);
    if (orgin == null) {
      return true;
    }
    UriComponents actualUrl = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString())
        .build();
    UriComponents originUrl = UriComponentsBuilder
        .fromOriginHeader(request.getHeader(HttpHeaders.ORIGIN)).build();
    return (actualUrl.getHost().equals(originUrl.getHost())
        && getPort(actualUrl) == getPort(originUrl));
  }

  private static int getPort(UriComponents uri) {
    int port = uri.getPort();
    if (port == -1) {
      if ("http".equals(uri.getScheme()) || "ws".equals(uri.getScheme())) {
        port = 80;
      } else if ("https".equals(uri.getScheme()) || "wss".equals(uri.getScheme())) {
        port = 443;
      }
    }
    return port;
  }
}
