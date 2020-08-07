package jdbc.java;//package jdbc.java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import javax.sql.DataSource;

public class JDBCSelectDemo {

  public static void main(String[] args) throws SQLException {
    JavaConfig javaConfig = new JavaConfig();
    final DataSource shardingDataSource = javaConfig.getShardingDataSource();

    final Connection conn = shardingDataSource.getConnection();
    conn.setAutoCommit(true);

    System.out.println("Creating statement...");
    String sql = "select * from  user where id = ?";
    final PreparedStatement pstmt = conn.prepareStatement(sql);
    pstmt.setLong(1,1L);

    ResultSet resultSet = pstmt.executeQuery();
    while (resultSet.next()) {
      System.out.println(resultSet.getInt(1));
      System.out.println(resultSet.getString(2));
    }
  }

}
