package org.transformer.user.dao;

import org.transformer.support.dao.jpa.repository.BaseDao;
import org.transformer.user.entity.User;

/**
 * 用户数据库操作接口.
 */
public interface UserDao extends BaseDao<User, Long> {

}
