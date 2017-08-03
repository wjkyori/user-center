package org.transformer.user;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * service测试基类 .
 */
@RunWith(SpringJUnit4ClassRunner.class) //这是JUnit的注解，通过这个注解让SpringJUnit4ClassRunner这个类提供Spring测试上下文。
@SpringBootTest(classes = ServiceTestApplication.class) // 指定spring-boot的启动类   
@ActiveProfiles("test") //指定测试配置文件application-test.properties
public class BaseServiceTest {

  protected Logger logger = LoggerFactory.getLogger(getClass());

  /**
   * mock初始化测试对象.
   */
  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
  }
}
