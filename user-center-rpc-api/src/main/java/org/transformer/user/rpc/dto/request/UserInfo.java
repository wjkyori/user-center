package org.transformer.user.rpc.dto.request;

/**
 * 用户信息.
 */
public class UserInfo implements java.io.Serializable {

  /**
   * 序列化ID.
   */
  private static final long serialVersionUID = 4666075277339804372L;

  /**
   * 编号.
   */
  private Long id;

  /**
   * 姓名.
   */
  private String name;

  /**
   * @return 获取  编号.
   */
  public Long getId() {
    return id;
  }

  /**
   * @param 设置 编号.
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return 获取  姓名.
   */
  public String getName() {
    return name;
  }

  /**
   * @param 设置 姓名.
   */
  public void setName(String name) {
    this.name = name;
  }
}
