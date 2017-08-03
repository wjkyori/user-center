package org.transformer.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * RPC服务提供者.
 */

@SpringBootApplication
@ComponentScan(basePackages = { "org.transformer" })
public class DaemonApplication implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(DaemonApplication.class, args);
  }

  /**
   * 保持后台运行.
   */
  @Override
  public void run(String... args) throws Exception {
    Thread.currentThread().join();
  }
}
