package org.transformer.user.rpc.dto.response;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 用户信息查询条件.
 */
public class UserQuery implements java.io.Serializable {

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

  @Override
  public boolean equals(Object obj) {
    if (null != obj && this.toString().equals(obj.toString())) {
      return true;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }

}
