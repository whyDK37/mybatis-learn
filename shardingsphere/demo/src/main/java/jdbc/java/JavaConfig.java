package jdbc.java;//package jdbc.java;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

public class JavaConfig {

  public DataSource getShardingDataSource() throws SQLException {
    ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
    shardingRuleConfig.getTableRuleConfigs().add(getUserTableRuleConfiguration());
    shardingRuleConfig.getTableRuleConfigs().add(getOrderItemTableRuleConfiguration());
    shardingRuleConfig.getBindingTableGroups().add("lg_user");
    shardingRuleConfig.getBroadcastTables().add("t_config");
    shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("id", "ds${user_id % 2}"));
//    shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(
//        new StandardShardingStrategyConfiguration("id, name", new ModuloShardingTableAlgorithm()));
    shardingRuleConfig.setDefaultTableShardingStrategyConfig(
        new StandardShardingStrategyConfiguration("id", new ModuloShardingTableAlgorithm()));
    return ShardingDataSourceFactory
        .createDataSource(createDataSourceMap(), shardingRuleConfig, new Properties());
  }

  private static KeyGeneratorConfiguration getKeyGeneratorConfiguration() {
    KeyGeneratorConfiguration result = new KeyGeneratorConfiguration("SNOWFLAKE", "id");
    return result;
  }

  TableRuleConfiguration getUserTableRuleConfiguration() {
    TableRuleConfiguration result = new TableRuleConfiguration("lg_user", "ds${0..1}.user");
    result.setKeyGeneratorConfig(getKeyGeneratorConfiguration());

    result.setDatabaseShardingStrategyConfig(
        new InlineShardingStrategyConfiguration("id", "ds${id % 2}"));
//    result.setDatabaseShardingStrategyConfig(
//        new ComplexShardingStrategyConfiguration("id, name", new ComplexKeysShardingAlgorithm() {
//
//          @Override
//          public Collection<String> doSharding(Collection availableTargetNames,
//              ComplexKeysShardingValue shardingValue) {
//            System.out.println("availableTargetNames = " + availableTargetNames);
//            System.out.println("getLogicTableName() = " + shardingValue.getLogicTableName());
//            System.out.println("getColumnNameAndRangeValuesMap() = " + shardingValue
//                .getColumnNameAndRangeValuesMap());
//            System.out.println("getColumnNameAndShardingValuesMap() = " + shardingValue
//                .getColumnNameAndShardingValuesMap());
//            return Lists.newArrayList(availableTargetNames);
//          }
//        }));
    return result;
  }

  TableRuleConfiguration getOrderItemTableRuleConfiguration() {
    TableRuleConfiguration result = new TableRuleConfiguration("lg_t_order_item",
        "ds${0..1}.t_order_item${0..1}");
    return result;
  }

  public Map<String, DataSource> createDataSourceMap() {
    Map<String, DataSource> result = new HashMap<>();
    result.put("ds0", DataSourceUtil.createDataSource("ds0"));
    result.put("ds1", DataSourceUtil.createDataSource("ds1"));
    return result;
  }
}
