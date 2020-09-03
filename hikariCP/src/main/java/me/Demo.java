package me;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public class Demo {

  private static final Object HOST = "localhost";
  private static final Object PORT = "3306";
  private static final Object dataSourceName = "test";

  public static void main(String[] args) throws SQLException {
    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setJdbcUrl(String.format(
        "jdbc:mysql://%s:%s/%s?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=UTF-8",
        HOST, PORT, dataSourceName));
//    dataSource.setMaximumPoolSize(30);
//    dataSource.setMinimumIdle(30);
    dataSource.setUsername("root");
    dataSource.setPassword("root");
    DataSource ds = dataSource;

    System.out.println(ds.isWrapperFor(HikariDataSource.class));
    System.out.println(dataSource.getMaximumPoolSize());
    final Connection connection = ds.getConnection();
    System.out.println(connection);

    connection.close();
    dataSource.close();
  }

}
