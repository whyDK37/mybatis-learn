package jdbc.java.account;//package jdbc.java;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.sql.DataSource;
import jdbc.java.DataSourceUtil;
import jdbc.java.account.alg.DatabaseShardingAlgorithm;
import jdbc.java.account.alg.TableShardingAlgorithm;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

public class JavaAccountConfig {

  public static void main(String[] args) throws SQLException {
    JavaAccountConfig javaAccountConfig = new JavaAccountConfig();
    final DataSource shardingDataSource = javaAccountConfig.getShardingDataSource();

    final Connection conn = shardingDataSource.getConnection();
    conn.setAutoCommit(true);

    System.out.println("Creating statement...");
    String sql = "select * from trans_account where seller_no=? and exp_date=?";
    final PreparedStatement pstmt = conn.prepareStatement(sql);
    pstmt.setString(1, "0");
    pstmt.setDate(2, new Date(System.currentTimeMillis()));

    final ResultSet resultSet1 = pstmt.executeQuery();
  }

  public DataSource getShardingDataSource() throws SQLException {
    ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
    shardingRuleConfig.getTableRuleConfigs().add(getAccountConf());
    return ShardingDataSourceFactory
        .createDataSource(createDataSourceMap(), shardingRuleConfig, new Properties());
  }

  private static KeyGeneratorConfiguration getKeyGeneratorConfiguration() {
    return new KeyGeneratorConfiguration("SNOWFLAKE", "id");
  }

  /**
   * 自定义分库分表
   *
   * @return
   */
  TableRuleConfiguration getAccountConf() {
    TableRuleConfiguration result = new TableRuleConfiguration("trans_account",
        "lbs_sharding_00.trans_account_202001");
    result.setKeyGeneratorConfig(getKeyGeneratorConfiguration());
    // 按月份分表
    result.setTableShardingStrategyConfig(
        new StandardShardingStrategyConfiguration("exp_date", new TableShardingAlgorithm()));
    // 按商家取模，
    result.setDatabaseShardingStrategyConfig(
        new StandardShardingStrategyConfiguration("seller_no", new DatabaseShardingAlgorithm()));
    return result;
  }

  public Map<String, DataSource> createDataSourceMap() {
    Map<String, DataSource> result = new HashMap<>();
    result.put("lbs_sharding_00", DataSourceUtil
        .createDataSource("192.168.179.111", "jf", "12345678", 3306, "lbs_sharding_00"));
    result.put("lbs_sharding_01", DataSourceUtil
        .createDataSource("192.168.179.111", "jf", "12345678", 3306, "lbs_sharding_01"));
    result.put("lbs_sharding_02", DataSourceUtil
        .createDataSource("192.168.179.111", "jf", "12345678", 3306, "lbs_sharding_02"));
    result.put("lbs_sharding_03", DataSourceUtil
        .createDataSource("192.168.179.111", "jf", "12345678", 3306, "lbs_sharding_03"));
    return result;
  }
}
