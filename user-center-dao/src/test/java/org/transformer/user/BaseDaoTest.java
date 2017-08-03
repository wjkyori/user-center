package org.transformer.user;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

/**
 * DAO单元测试基类，默认开启事务并回滚.
 */
@RunWith(SpringJUnit4ClassRunner.class) //这是JUnit的注解，通过这个注解让SpringJUnit4ClassRunner这个类提供Spring测试上下文。
@SpringBootTest(classes = DaoTestApplication.class) // 指定spring-boot的启动类
@Transactional
@Rollback(true)
@ActiveProfiles("test")
public class BaseDaoTest {

  protected Logger logger = LoggerFactory.getLogger(getClass());

}
