package org.transformer.user.shiro.pac4j;

import io.buji.pac4j.profile.ShiroProfileManager;

import org.apache.commons.lang3.StringUtils;
import org.pac4j.core.client.Client;
import org.pac4j.core.context.HttpConstants;
import org.pac4j.core.context.Pac4jConstants;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.engine.DefaultSecurityLogic;
import org.pac4j.core.exception.HttpAction;
import org.pac4j.core.http.AjaxRequestResolver;
import org.pac4j.core.http.DefaultAjaxRequestResolver;
import org.springframework.http.HttpHeaders;

import java.util.List;

public class SecurityLogic<R, C extends WebContext> extends DefaultSecurityLogic<R, C> {

  /**
   * 默认使用shiroProfileManager，保存用户信息到shiro.
   */
  public SecurityLogic() {
    this.setProfileManagerFactory(ShiroProfileManager::new);
  }

  /** 是否跳转到AJAX的调用源.  */
  private boolean redirectToReferer = true;

  /**Ajax 请求解析器. */
  private AjaxRequestResolver ajaxRequestResolver = new DefaultAjaxRequestResolver();

  @Override
  @SuppressWarnings("rawtypes")
  protected HttpAction redirectToIdentityProvider(final C context,
      final List<Client> currentClients) throws HttpAction {
    try {
      return super.redirectToIdentityProvider(context, currentClients);
    } catch (HttpAction e) {
      if (e.getCode() == HttpConstants.UNAUTHORIZED) {
        //未授权的重定向,是否重定向到调用页面      
        if (redirectToReferer) {
          if (ajaxRequestResolver.isAjax(context)) {
            String referer = context.getRequestHeader(HttpHeaders.REFERER);
            if (StringUtils.isNoneBlank(referer)) {
              context.setSessionAttribute(Pac4jConstants.REQUESTED_URL, referer);
            }
          }
        }
      }
      throw e;
    }

  }

  /**
   * @return 获取  Ajax请求解析器.
   */
  public AjaxRequestResolver getAjaxRequestResolver() {
    return ajaxRequestResolver;
  }

  /**
   * @param 设置 Ajax请求解析器.
   */
  public void setAjaxRequestResolver(AjaxRequestResolver ajaxRequestResolver) {
    this.ajaxRequestResolver = ajaxRequestResolver;
  }

  /**
   * @return 获取  是否跳转到AJAX的调用源.
   */
  public boolean isRedirectToReferer() {
    return redirectToReferer;
  }

  /**
   * @param 设置 是否跳转到AJAX的调用源.
   */
  public void setRedirectToReferer(boolean redirectToReferer) {
    this.redirectToReferer = redirectToReferer;
  }

}
