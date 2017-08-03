package org.transformer.user.enumcode;

/**
 * 性别.
 */
public enum Gender {

  male("男"),

  female("女"),

  other("其他");

  /** 名称. */
  private String text;

  private Gender(String text) {
    this.text = text;
  }

  /**
   * @return 获取  名称.
   */
  public String getText() {
    return text;
  }
}
