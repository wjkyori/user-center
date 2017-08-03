package org.transformer.user;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * rpc provider 测试基类 .
 */
@RunWith(SpringJUnit4ClassRunner.class) //这是JUnit的注解，通过这个注解让SpringJUnit4ClassRunner这个类提供Spring测试上下文。
@SpringBootTest(classes = RpcProviderTestApplication.class) // 指定spring-boot的启动类   
@ActiveProfiles("test") //指定测试配置文件application-test.properties
public class BaseRpcProviderTest {

  protected Logger logger = LoggerFactory.getLogger(getClass());

  /**
   * 先休眠3秒，等待rpc服务连接创建完毕.
   */
  @Before
  public void init() {
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
