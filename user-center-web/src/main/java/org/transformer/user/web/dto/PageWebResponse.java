package org.transformer.user.web.dto;

import java.util.List;

/**
 * 分页请求返回值.
 */
public class PageWebResponse<T> extends BaseDto {

  /**
   * 序列号.
   */
  private static final long serialVersionUID = 2069144978591646926L;

  /**
   * 总记录数.
   */
  private Long total;

  /**
   * 返回数据.
   */
  private List<T> data;

  public PageWebResponse(List<T> data) {
    this(data, null);
  }

  public PageWebResponse(List<T> data, Long total) {
    this.data = data;
    this.total = total;
  }

  /**
   * @return 获取  返回数据.
   */
  public List<T> getData() {
    return data;
  }

  /**
   * @param 设置 返回数据.
   */
  public void setData(List<T> data) {
    this.data = data;
  }

  /**
   * @return 获取  总记录数.
   */
  public Long getTotal() {
    return total;
  }

  /**
   * @param 设置 总记录数.
   */
  public void setTotal(Long total) {
    this.total = total;
  }
}
