package org.transformer.user.shiro.pac4j;


import org.apache.shiro.SecurityUtils;
import org.pac4j.core.config.Config;
import org.pac4j.core.context.HttpConstants;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.engine.SecurityGrantedAccessAdapter;
import org.pac4j.core.exception.HttpAction;
import org.pac4j.core.http.HttpActionAdapter;


public class LogoutLogic<R, C extends WebContext>
    extends org.transformer.user.shiro.pac4j.SecurityLogic<R, C> {

  /**
   * 跳转地址参数.
   */
  private static final String REDIRECT_URL = "redirect";

  /**
   * cas退出地址.
   */
  private String casServerLogoutUrl;

  /**
   * 退出后跳转地址.
   */
  private String redirectUrl;

  @Override
  public R perform(final C context, final Config config,
                   final SecurityGrantedAccessAdapter<R, C> securityGrantedAccessAdapter,
                   final HttpActionAdapter<R, C> httpActionAdapter, final String clients,
                   final String authorizers, final String matchers, final Boolean inputMultiProfile,
                   final Object... parameters) {
    SecurityUtils.getSubject().logout();
    String redirectUrl = getRedirectUrlFromContext(context);
    HttpAction action;
    if (this.getAjaxRequestResolver().isAjax(context)) {
      context.setResponseHeader(HttpConstants.LOCATION_HEADER, redirectUrl);
      action = HttpAction.ok("退出成功", context, "{\"" + REDIRECT_URL + "\":\"" + redirectUrl + "\"}");
    } else {
      action = HttpAction.redirect("退出成功", context, redirectUrl);
    }
    return httpActionAdapter.adapt(action.getCode(), context);
  }

  private String getRedirectUrlFromContext(C context) {
    String logout = casServerLogoutUrl;
    String redirectUrl = context.getRequestParameter(REDIRECT_URL);
    if (redirectUrl == null) {
      redirectUrl = getRedirectUrl();
    }
    if (redirectUrl != null && !redirectUrl.trim().equals("")) {
      logout += ("?service=" + redirectUrl);
    }
    return logout;
  }

  /**
   * 获取cas退出地址 .
   *
   * @return casServerLogoutUrl cas退出地址
   */
  public String getCasServerLogoutUrl() {
    return casServerLogoutUrl;
  }

  /**
   * 设置cas退出地址 .
   *
   * @param casServerLogoutUrl cas退出地址
   */
  public void setCasServerLogoutUrl(String casServerLogoutUrl) {
    this.casServerLogoutUrl = casServerLogoutUrl;
  }

  /**
   * 获取退出后跳转地址 .
   *
   * @return redirectUrl 退出后跳转地址
   */
  public String getRedirectUrl() {
    return redirectUrl;
  }

  /**
   * 设置退出后跳转地址 .
   *
   * @param redirectUrl 退出后跳转地址
   */
  public void setRedirectUrl(String redirectUrl) {
    this.redirectUrl = redirectUrl;
  }

}
