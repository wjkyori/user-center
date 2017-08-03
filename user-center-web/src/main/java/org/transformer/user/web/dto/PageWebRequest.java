package org.transformer.user.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

/**
 * 前端分页请求基类.
 */
public class PageWebRequest extends BaseDto {

  /**
   * 序列号.
   */
  private static final long serialVersionUID = -2520308163459883974L;

  /**
   * 当前页码.
   */
  private Integer page;

  /**
   * 每页记录数.
   */
  private Integer pageSize;

  /** 排序字段. */
  private String orderKey;

  /** 排序方式. */
  private String orderType;

  /** 倒序排列方式. */
  private static final String ORDER_TYPE_DESC = "descend";

  /**
   * @return 获取  排序方式.
   */
  public String getOrderType() {
    return orderType;
  }

  /**
   * @param 设置 排序方式.
   */
  public void setOrderType(String orderType) {
    this.orderType = orderType;
  }

  @JsonIgnore
  private Pageable pageable;

  /**
   * @return 获取  当前页码.
   */
  public Integer getPage() {
    return page;
  }

  /**
   * @param 设置 当前页码.
   */
  public void setPage(Integer page) {
    this.page = page;
  }

  /**
   * @return 获取  每页记录数.
   */
  public Integer getPageSize() {
    return pageSize;
  }

  /**
   * @param 设置 每页记录数.
   */
  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  /**
   * @return 获取  排序字段.
   */
  public String getOrderKey() {
    return orderKey;
  }

  /**
   * @param 设置 排序字段.
   */
  public void setOrderKey(String orderKey) {
    this.orderKey = orderKey;
  }

  /** 是否倒序. */
  public boolean isDesc() {
    if (StringUtils.isNotBlank(orderType) && ORDER_TYPE_DESC.equals(orderType)) {
      return true;
    } else {
      return false;
    }
  }

  /** 转化成jpa排序对象. */
  public Sort toSort() {
    if (StringUtils.isNotBlank(orderKey)) {
      return new Sort(isDesc() ? Direction.DESC : Direction.ASC, orderKey);
    } else {
      return null;
    }
  }

  /**
   * 获取分页信息.
   * @return Pageable
   */
  public Pageable getPageable() {
    if (pageable == null && page != null && pageSize != null) {
      pageable = new org.springframework.data.domain.PageRequest(page - 1, pageSize, toSort());

    }
    return pageable;
  }
}
