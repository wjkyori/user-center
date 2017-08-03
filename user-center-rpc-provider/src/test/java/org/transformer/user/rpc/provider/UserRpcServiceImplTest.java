package org.transformer.user.rpc.provider;

import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.util.Assert;
import org.transformer.user.BaseRpcProviderTest;
import org.transformer.user.entity.User;
import org.transformer.user.rpc.dto.response.UserQuery;
import org.transformer.user.service.UserService;

import javax.annotation.Resource;

public class UserRpcServiceImplTest extends BaseRpcProviderTest {

  @Mock
  private UserService userService;

  @InjectMocks
  @Resource
  private UserRpcServiceImpl userRpcServiceImpl;

  @Test
  public void testFind() {
    BDDMockito.given(userService.findOne(1L)).willReturn(new User());
    UserQuery userQuery = new UserQuery();
    userQuery.setId(1L);
    Assert.notNull(this.userRpcServiceImpl.find(userQuery), "查询不应该为空");
  }

}
