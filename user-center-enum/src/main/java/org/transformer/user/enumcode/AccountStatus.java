package org.transformer.user.enumcode;

/**
 * 状态.
 */
public enum AccountStatus {

  enabled("已启用"),

  disabled("已禁用");

  /** 名称. */
  private String text;

  private AccountStatus(String text) {
    this.text = text;
  }

  /**
   * @return 获取  名称.
   */
  public String getText() {
    return text;
  }
}
