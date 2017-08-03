package org.transformer.user.rpc.provider;

import com.alibaba.dubbo.config.annotation.Service;

import org.transformer.user.entity.User;
import org.transformer.user.rpc.dto.request.UserInfo;
import org.transformer.user.rpc.dto.response.UserQuery;
import org.transformer.user.rpc.service.UserRpcService;
import org.transformer.user.service.UserService;

import javax.annotation.Resource;

@Service
public class UserRpcServiceImpl implements UserRpcService {

  @Resource
  private UserService userService;

  @Override
  public UserInfo find(UserQuery request) {
    User user = this.userService.findOne(request.getId());
    if (user != null) {
      UserInfo userInfo = new UserInfo();
      userInfo.setId(1L);
      userInfo.setName(user.getName());
      return userInfo;
    }
    return null;
  }

}