package jdbc.java.account;//package jdbc.java;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.shardingsphere.core.yaml.config.sharding.YamlRootShardingConfiguration;
import org.apache.shardingsphere.core.yaml.constructor.YamlRootShardingConfigurationConstructor;
import org.apache.shardingsphere.core.yaml.swapper.ShardingRuleConfigurationYamlSwapper;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.apache.shardingsphere.underlying.common.yaml.engine.YamlEngine;

public class AccountYmlConfig {

  public static void main(String[] args) throws SQLException, IOException {
    File yamlFile = new File(
        "D:\\workspace\\mygit\\mybatis-learn\\shardingsphere\\demo\\src\\main\\resources\\account.yml");

    YamlRootShardingConfiguration config = YamlEngine
        .unmarshal(yamlFile, YamlRootShardingConfiguration.class,
            new YamlRootShardingConfigurationConstructor());
    DataSource dataSource = ShardingDataSourceFactory
        .createDataSource(config.getDataSources(),
            new ShardingRuleConfigurationYamlSwapper().swap(config.getShardingRule()),
            config.getProps());

//    DataSource dataSource = YamlShardingDataSourceFactory.createDataSource(yamlFile);

    final Connection conn = dataSource.getConnection();
    conn.setAutoCommit(true);

    System.out.println("Creating statement...");
    String sql = "select id,seller_no,exp_date from trans_account where seller_no=? and exp_date=?";
    final PreparedStatement pstmt = conn.prepareStatement(sql);
    pstmt.setString(1, "1");
    pstmt.setDate(2, new Date(2020 - 1900, 6, 31));

    final ResultSet resultSet1 = pstmt.executeQuery();
    while (resultSet1.next()) {
      System.out.println(resultSet1.getLong(1));
      System.out.println(resultSet1.getString(2));
      System.out.println(resultSet1.getDate(3));
    }
  }
}
