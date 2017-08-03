package org.transformer.user.shiro.pac4j;

import io.buji.pac4j.profile.ShiroProfileManager;

import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.engine.DefaultCallbackLogic;
import org.pac4j.core.exception.HttpAction;
import org.pac4j.core.exception.TechnicalException;
import org.pac4j.core.http.AjaxRequestResolver;
import org.pac4j.core.http.DefaultAjaxRequestResolver;
import org.pac4j.core.http.HttpActionAdapter;

public class CallbackLogic<R, C extends WebContext> extends DefaultCallbackLogic<R, C> {

  /**
   * 默认使用shiroProfileManager，保存用户信息到shiro.
   */
  public CallbackLogic() {
    this.setProfileManagerFactory(ShiroProfileManager::new);
  }

  /**Ajax 请求解析器. */
  private AjaxRequestResolver ajaxRequestResolver = new DefaultAjaxRequestResolver();

  /** Access-Token 字符串. */
  private String accessTokenName = "access-token";

  @Override
  public R perform(final C context, final Config config,
      final HttpActionAdapter<R, C> httpActionAdapter, final String inputDefaultUrl,
      final Boolean inputMultiProfile, final Boolean inputRenewSession) {
    try {
      return super.perform(context, config, httpActionAdapter, inputDefaultUrl, inputMultiProfile,
          inputRenewSession);
    } catch (TechnicalException exception) {
      logger.error("验证登录信息错误", exception);
      final Clients clients = config.getClients();
      HttpAction action;
      try {
        action = clients.findClient(context).redirect(context);
      } catch (HttpAction httpAction) {
        action = httpAction;
      }
      return httpActionAdapter.adapt(action.getCode(), context);
    }
  }

  @Override
  protected HttpAction redirectToOriginallyRequestedUrl(final C context, final String defaultUrl) {
    //Ajax请求，返回一个token
    if (ajaxRequestResolver.isAjax(context)) {
      return HttpAction.ok("AJAX request authorized", context,
          "{\"" + accessTokenName + "\":\"" + context.getSessionIdentifier() + "\"}");
    } else {
      return super.redirectToOriginallyRequestedUrl(context, defaultUrl);
    }
  }

  /**
   * @return 获取  Access-Token字符串.
   */
  public String getAccessTokenName() {
    return accessTokenName;
  }

  /**
   * @param 设置 Access-Token字符串.
   */
  public void setAccessTokenName(String accessTokenName) {
    this.accessTokenName = accessTokenName;
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

}
