package org.transformer.user;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

/**
 * H2的配置.
 * 可以通过浏览器访问数据库http://localhost:8082
 * 默认的JDBC URL : jdbc:h2:mem:testdb
 */
@Configuration
public class H2ServerConfiguration {

  /**
   * tcp连接端口号，默认9092.
   */
  private String h2TcpPort = "9092";

  /**
   * WEB控制台端口号，默认8082.
   */
  //private String h2WebPort = "8082";
  @Value("${h2.web.port:8082}")
  private String h2WebPort;

  @Bean
  public Server h2TcpServer() throws SQLException {
    return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", h2TcpPort).start();
  }

  @Bean
  public Server h2WebServer() throws SQLException {
    return Server.createWebServer("-web", "-webAllowOthers", "-webPort", h2WebPort).start();
  }

}