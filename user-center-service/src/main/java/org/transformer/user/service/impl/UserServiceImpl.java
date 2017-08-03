package org.transformer.user.service.impl;

import org.springframework.stereotype.Service;
import org.transformer.support.service.impl.BaseServiceImpl;
import org.transformer.user.dao.UserDao;
import org.transformer.user.entity.User;
import org.transformer.user.service.UserService;

/**
 * 用户业务操作实现类.
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, UserDao> implements UserService {

}
