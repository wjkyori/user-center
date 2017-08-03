package org.transformer.user.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.transformer.user.BaseServiceTest;
import org.transformer.user.dao.UserDao;
import org.transformer.user.entity.User;

import javax.annotation.Resource;

public class UserServiceImplTest extends BaseServiceTest {

  @Mock
  private UserDao userDao;

  @InjectMocks
  @Resource
  private UserServiceImpl userServiceImpl;

  @Test
  public void testFindOne() throws Exception {
    User user = new User();
    user.setId(1L);
    user.setName("马钧");
    BDDMockito.given(userDao.findOne(1L)).willReturn(user);
    Assert.assertEquals(this.userServiceImpl.findOne(1L).getName(), "马钧");
  }

}
