package org.transformer.user.web.dto.manage.account;

import org.apache.commons.lang3.StringUtils;
import org.transformer.support.dao.jpa.search.SearchOperator;
import org.transformer.support.dao.jpa.search.Searchable;
import org.transformer.support.dao.jpa.search.filter.SearchFilter;
import org.transformer.support.dao.jpa.search.filter.SearchFilterHelper;
import org.transformer.user.web.dto.PageWebRequest;

import java.util.Date;

/**
 * 用户查询条件./
 */
public class UserQuery extends PageWebRequest {

  /**
   * 序列号.
   */
  private static final long serialVersionUID = -5536298358123803492L;

  /**用户名. */
  private String userName;

  /**
   * 状态.
   */
  private String[] status;

  /**
   * 性别.
   */
  private String[] gender;

  /**
   * 用户名\姓名\手机号.
   */
  private String filter;

  /**
   * 时间过滤条件.
   */
  private String timeFilterType;

  /**
   * 时间条件.
   */
  private Date[] filterTimes;

  /**
   * @return 获取  用户名.
   */
  public String getUserName() {
    return userName;
  }

  /**
   * @param 设置 用户名.
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  /**
  * @return 获取  状态.
  */
  public String[] getStatus() {
    return status;
  }

  /**
   * @param 设置 状态.
   */
  public void setStatus(String[] status) {
    this.status = status;
  }

  /**
   * @return 获取  性别.
   */
  public String[] getGender() {
    return gender;
  }

  /**
   * @param 设置 性别.
   */
  public void setGender(String[] gender) {
    this.gender = gender;
  }

  /**
   * @return 获取  用户\名姓名\手机号.
   */
  public String getFilter() {
    return filter;
  }

  /**
   * @param 设置 用户名\姓名\手机号.
   */
  public void setFilter(String filter) {
    this.filter = filter;
  }

  /**
   * @return 获取  时间过滤条件.
   */
  public String getTimeFilterType() {
    return timeFilterType;
  }

  /**
   * @param 设置 时间过滤条件.
   */
  public void setTimeFilterType(String timeFilterType) {
    this.timeFilterType = timeFilterType;
  }

  /**
   * @return 获取  时间条件.
   */
  public Date[] getFilterTimes() {
    return filterTimes;
  }

  /**
   * @param 设置 时间条件.
   */
  public void setFilterTimes(Date[] filterTimes) {
    this.filterTimes = filterTimes;
  }

  /** 转化为searchable查询条件. */
  public Searchable toSearchable() {
    Searchable searchable = Searchable.newSearchable();
    searchable.setPage(getPageable());
    //用户名全匹配
    if (StringUtils.isNoneBlank(userName)) {
      searchable.addSearchFilter("userName", SearchOperator.eq, userName);
    }
    //用户名、姓名、手机 模糊查询
    if (StringUtils.isNoneBlank(filter)) {
      SearchFilter s1 = SearchFilterHelper.newCondition("userName", SearchOperator.like, filter);
      SearchFilter s2 = SearchFilterHelper.newCondition("name", SearchOperator.like, filter);
      SearchFilter s3 = SearchFilterHelper.newCondition("mobile", SearchOperator.like, filter);
      searchable.addSearchFilter(SearchFilterHelper.or(s1, s2, s3));
    }
    //时间    
    if (StringUtils.isNoneBlank(timeFilterType) && filterTimes != null && filterTimes.length > 0) {
      if (filterTimes[0] != null) {
        searchable.addSearchFilter(timeFilterType, SearchOperator.gte, filterTimes[0]);
      }
      if (filterTimes.length > 1 && filterTimes[1] != null) {
        searchable.addSearchFilter(timeFilterType, SearchOperator.lte, filterTimes[1]);
      }
    }
    //性别
    if (gender != null && gender.length > 0) {
      searchable.addSearchFilter("gender", SearchOperator.in, gender);
    }
    //状态
    if (status != null && status.length > 0) {
      searchable.addSearchFilter("status", SearchOperator.in, status);
    }
    return searchable;
  }

}
