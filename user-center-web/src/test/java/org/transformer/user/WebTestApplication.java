package org.transformer.user;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * WEB测试用的启动类.
 */
@SpringBootApplication
@ComponentScan(basePackages = { "org.transformer" })
public class WebTestApplication {

}