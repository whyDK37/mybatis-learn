package me;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class Demo {

  private static final Object HOST = "localhost";
  private static final Object PORT = "3306";
  private static final Object dataSourceName = "test";
  public static void main(String[] args) throws SQLException {
    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setJdbcUrl(String.format("jdbc:mysql://%s:%s/%s?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=UTF-8", HOST, PORT, dataSourceName));
    dataSource.setUsername("root");
    dataSource.setPassword("root");

    final Connection connection = dataSource.getConnection();
    System.out.println(connection);
  }

}
