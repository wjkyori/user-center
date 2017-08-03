package org.transformer.user.entity;

import org.transformer.support.entity.BaseEntity;
import org.transformer.user.enumcode.AccountStatus;
import org.transformer.user.enumcode.Gender;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户信息实体.
 */
@Entity
@Table(name = "user")
public class User extends BaseEntity {

  /**
   * 反序列版本编号.
   */
  private static final long serialVersionUID = -6308531050810255644L;

  /**
   * 主键.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  /** 用户名. */
  @Column(name = "user_name")
  private String userName;

  /** 名字. */
  private String name;

  /** 手机号. */
  private String mobile;

  /** 邮箱. */
  private String email;

  /** 性别. */
  @Enumerated(EnumType.STRING)
  private Gender gender;

  /** 状态. */
  @Enumerated(EnumType.STRING)
  private AccountStatus status;

  @Override
  public Long getId() {
    return this.id;
  }

  @Override
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

}
