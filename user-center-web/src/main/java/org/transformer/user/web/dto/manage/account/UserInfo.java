package org.transformer.user.web.dto.manage.account;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;
import org.transformer.user.entity.User;
import org.transformer.user.enumcode.AccountStatus;
import org.transformer.user.enumcode.Gender;
import org.transformer.user.web.dto.BaseDto;
import org.transformer.validator.constraints.Mobile;

import java.util.Date;

public class UserInfo extends BaseDto {

  /**
   * 序列化版本号.
   */
  private static final long serialVersionUID = -211892840896620861L;

  /**
   * 主键.
   */
  private Long id;

  /**
   * 用户名.
   */
  @NotEmpty(message = "用户名不能为空.")
  private String userName;

  /**
   * 名字.
   */
  @NotEmpty(message = "名字不能为空.")
  private String name;

  /**
   * 手机号.
   */
  @NotEmpty(message = "手机号不能为空.")
  @Mobile
  private String mobile;

  /**
   * 邮箱.
   */
  @Email(message = "无效的邮箱地址.")
  private String email;

  /**
   * 性别.
   */
  private Gender gender;

  /**
   * 状态.
   */
  private AccountStatus status;

  /**
   * 创建时间.
   */
  private Date createTime;

  /**
   * 更新时间.
   */
  private Date modifyTime;

  public UserInfo() {

  }

  public UserInfo(User user) {
    BeanUtils.copyProperties(user, this);
  }

  /**
   * @return 获取  主键.
   */
  public Long getId() {
    return id;
  }

  /**
   * @param 设置 主键.
   */
  public void setId(Long id) {
    this.id = id;
  }

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
   * @return 获取  名字.
   */
  public String getName() {
    return name;
  }

  /**
   * @param 设置 名字.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return 获取  手机号.
   */
  public String getMobile() {
    return mobile;
  }

  /**
   * @param 设置 手机号.
   */
  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  /**
   * @return 获取  邮箱.
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param 设置 邮箱.
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * @return 获取  性别.
   */
  public Gender getGender() {
    return gender;
  }

  /**
   * @param 设置 性别.
   */
  public void setGender(Gender gender) {
    this.gender = gender;
  }

  /**
   * @return 获取  状态.
   */
  public AccountStatus getStatus() {
    return status;
  }

  /**
   * @param 设置 状态.
   */
  public void setStatus(AccountStatus status) {
    this.status = status;
  }

  /**
   * @return 获取  创建时间.
   */
  public Date getCreateTime() {
    return createTime;
  }

  /**
   * @param 设置 创建时间.
   */
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  /**
   * @return 获取  更新时间.
   */
  public Date getModifyTime() {
    return modifyTime;
  }

  /**
   * @param 设置 更新时间.
   */
  public void setModifyTime(Date modifyTime) {
    this.modifyTime = modifyTime;
  }

}
