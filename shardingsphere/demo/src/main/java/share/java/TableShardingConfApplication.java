package share.java;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TableShardingConfApplication {


  public static void main(String[] args) throws SQLException {
    ConfigurableApplicationContext applicationContext = SpringApplication
        .run(TableShardingConfApplication.class, args);
    System.out.println("started successfully!");

    DataSource dataSource = applicationContext.getBean(DataSource.class);
    Connection connection = dataSource.getConnection();
    TableShardingConf.doSomeThing(connection);

  }

}
