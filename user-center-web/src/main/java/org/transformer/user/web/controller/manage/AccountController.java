package org.transformer.user.web.controller.manage;

import com.google.common.collect.Lists;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.transformer.exception.ServiceException;
import org.transformer.support.controller.BaseController;
import org.transformer.support.dao.jpa.search.Searchable;
import org.transformer.user.entity.User;
import org.transformer.user.enumcode.AccountStatus;
import org.transformer.user.service.UserService;
import org.transformer.user.web.dto.PageWebResponse;
import org.transformer.user.web.dto.manage.account.UserInfo;
import org.transformer.user.web.dto.manage.account.UserQuery;

import java.util.Map;

import javax.validation.Valid;

@RestController
@RequestMapping("/user/manage/accounts")
public class AccountController extends BaseController<User, UserService> {

  /**
   * 启用.
   */
  private static final String ACCOUNT_STATUS_ENABLE = "enable";

  /**
   * 禁用.
   */
  private static final String ACCOUNT_STATUS_DISABLE = "disable";

  /**
   * 查找用户.
   *
   * @param userQuery 查询条件
   * @return 用户信息
   */
  @RequestMapping(method = {RequestMethod.GET})
  public PageWebResponse<UserInfo> findUser(UserQuery userQuery) {
    Searchable searchable = userQuery != null ? userQuery.toSearchable() : null;
    Page<User> users = this.service.findAll(searchable);
    //结果集
    if (users != null && CollectionUtils.isNotEmpty(users.getContent())) {
      PageWebResponse<UserInfo> response = new PageWebResponse<UserInfo>(Lists.newArrayList());
      for (User user : users) {
        UserInfo userInfo = new UserInfo(user);
        response.getData().add(userInfo);
      }
      response.setTotal(users.getTotalElements());
      return response;
    }
    return null;
  }

  /**
   * 创建用户.
   *
   * @param userInfo 用户信息
   */
  @RequestMapping(method = {RequestMethod.POST})
  public void create(@RequestBody UserInfo userInfo) {
    User user = new User();
    BeanUtils.copyProperties(userInfo, user, "id");
    user.setStatus(AccountStatus.enabled);
    this.service.save(user);
  }

  /**
   * 获取用户信息.
   *
   * @param id 主键编号
   */
  @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
  public UserInfo create(@PathVariable Long id) {
    User user = this.service.findOne(id);
    if (user != null) {
      return new UserInfo(user);
    }
    return null;
  }

  /**
   * 更新用户.
   *
   * @param id       主键编号
   * @param userInfo 要更新的内容.
   */
  @RequestMapping(value = "/{id}", method = {RequestMethod.PATCH})
  public void update(@PathVariable Long id, @RequestBody @Valid UserInfo userInfo) {
    User user = this.service.findOne(id);
    if (user != null) {
      BeanUtils.copyProperties(userInfo, user, "id", "status");
      this.service.save(user);
    }
  }

  /**
   * 改变用户状态.
   *
   * @param id     主键id
   * @param object type类型
   */
  @RequestMapping(value = "/{id}/status", method = {RequestMethod.PUT})
  public void modify(@PathVariable Long id, @RequestBody Map<String, String> object) {
    User user = this.service.findOne(id);
    if (user != null) {
      String type = object.get("type");
      if (type != null) {
        if (type.equals(ACCOUNT_STATUS_DISABLE)) {
          if (user.getStatus() == AccountStatus.disabled) {
            throw new ServiceException("账号已经停用，请勿重复操作！");
          }
          user.setStatus(AccountStatus.disabled);
        } else if (type.equals(ACCOUNT_STATUS_ENABLE)) {
          if (user.getStatus() == AccountStatus.enabled) {
            throw new ServiceException("账号已经启用，请勿重复操作！");
          }
          user.setStatus(AccountStatus.enabled);
        }
      }
      this.service.save(user);
    }
  }

}
