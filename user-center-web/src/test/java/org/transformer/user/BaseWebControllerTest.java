package org.transformer.user;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Web 测试基类 .
 */
@RunWith(SpringJUnit4ClassRunner.class) //这是JUnit的注解，通过这个注解让SpringJUnit4ClassRunner这个类提供Spring测试上下文。
@SpringBootTest(classes = WebTestApplication.class) // 指定spring-boot的启动类   
@ActiveProfiles("test") //指定测试配置文件application-test.properties
public class BaseWebControllerTest {

  protected Logger logger = LoggerFactory.getLogger(getClass());

}
