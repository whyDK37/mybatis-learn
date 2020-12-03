package share.java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.sql.DataSource;
import jdbc.java.DataSourceUtil;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

/**
 * 单库分表 https://shardingsphere.apache.org/document/legacy/4.x/document/en/manual/sharding-jdbc/configuration/config-java/#configuration-instance
 */
public class TableShardingConf {

  public static void main(String[] args) throws SQLException {
    TableShardingConf shardingConf = new TableShardingConf();
    DataSource shardingDataSource = shardingConf.getShardingDataSource();
    Connection connection = shardingDataSource.getConnection();

    doSomeThing(connection);

  }

  static void doSomeThing(Connection connection) throws SQLException {
    PreparedStatement preparedStatement = connection
        .prepareStatement("select * from user where id in (11)");

    ResultSet resultSet = preparedStatement.executeQuery();

    while (resultSet.next()) {
      System.out.println("id = " + resultSet.getInt("id"));
      System.out.println("name = " + resultSet.getString("name"));
    }

    // 插入数据按照 id 路由
    PreparedStatement insert = connection
        .prepareStatement("insert into user(id,name,ti) values(?,?,?)");
    insert.setLong(1, 12);
    insert.setString(2, "in");
    insert.setInt(3, 2);
    int i = insert.executeUpdate();
    System.out.println("insert : " + i);
  }

  DataSource getShardingDataSource() throws SQLException {
    ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
    shardingRuleConfig.getTableRuleConfigs().add(getUserTableRuleConfiguration());
    Properties props = new Properties();
    props.put("sql.show", "true");
    return ShardingDataSourceFactory
        .createDataSource(createDataSourceMap(), shardingRuleConfig, props);
  }


  TableRuleConfiguration getUserTableRuleConfiguration() {
    TableRuleConfiguration result = new TableRuleConfiguration("user",
        "ds0.user_$->{0..1023}");
    result.setTableShardingStrategyConfig(
        new InlineShardingStrategyConfiguration("id", "user_${id % 1024}"));
    return result;
  }

  Map<String, DataSource> createDataSourceMap() {
    Map<String, DataSource> result = new HashMap<>();
    result.put("ds0", DataSourceUtil.createDataSource("ds0"));
    return result;
  }
}
