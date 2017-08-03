package org.transformer.user.rpc.service;

import org.transformer.user.rpc.dto.request.UserInfo;
import org.transformer.user.rpc.dto.response.UserQuery;

/**
 * 用户信息相关RPC服务.
 */
public interface UserRpcService {

  /**
   * 查询用户信息.
   * @param request 查询条件
   * @return 查询结果
   */
  public UserInfo find(UserQuery request);

}