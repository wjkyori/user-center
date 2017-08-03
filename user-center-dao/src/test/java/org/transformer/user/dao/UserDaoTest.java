package org.transformer.user.dao;

import org.junit.Test;
import org.springframework.util.Assert;
import org.transformer.user.BaseDaoTest;
import org.transformer.user.entity.User;

import javax.annotation.Resource;

public class UserDaoTest extends BaseDaoTest {

  @Resource
  private UserDao userDao;

  @Test
  public void testFindOne() throws Exception {
    Assert.notNull(this.userDao.findOne(1L), "不为空");
  }

  @Test
  public void testInsert() throws Exception {
    User user = new User();
    user.setId(9L);
    this.userDao.save(user);
    Assert.notNull(this.userDao.findOne(9L), "不为空");
  }

  @Test
  public void testFindAll() throws Exception {
    Assert.notNull(this.userDao.findAll(), "不为空");
  }

}
