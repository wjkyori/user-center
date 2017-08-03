package org.transformer.user.web.dto;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 前端传输基类.
 */
public class BaseDto implements java.io.Serializable {

  /**
   * 序列化版本号.
   */
  private static final long serialVersionUID = 7179808461247871014L;

  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }

}
