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
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

/**
 * https://shardingsphere.apache.org/document/legacy/4.x/document/en/manual/sharding-jdbc/configuration/config-java/#configuration-instance
 */
public class DataShardingConf {

  public static void main(String[] args) throws SQLException {
    DataShardingConf shardingConf = new DataShardingConf();
    DataSource shardingDataSource = shardingConf.getShardingDataSource();
    Connection connection = shardingDataSource.getConnection();
    PreparedStatement preparedStatement = connection
        .prepareStatement("select * from user where id = 1");

    ResultSet resultSet = preparedStatement.executeQuery();

    while (resultSet.next()) {
      System.out.println("resultSet.getInt(\"id\") = " + resultSet.getInt("id"));
    }

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
        "ds0.user_$->{0..1}");
    result.setTableShardingStrategyConfig(
        new InlineShardingStrategyConfiguration("id", "user_${id % 2}"));
//    result.setKeyGeneratorConfig(getKeyGeneratorConfiguration());
    return result;
  }

  Map<String, DataSource> createDataSourceMap() {
    Map<String, DataSource> result = new HashMap<>();
    result.put("ds0", DataSourceUtil.createDataSource("ds0"));
    return result;
  }
}
