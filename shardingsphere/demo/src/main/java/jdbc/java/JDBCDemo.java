package jdbc.java;//package jdbc.java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import javax.sql.DataSource;

public class JDBCDemo {

  public static void main(String[] args) throws SQLException {
    JavaConfig javaConfig = new JavaConfig();
    final DataSource shardingDataSource = javaConfig.getShardingDataSource();

    final Connection conn = shardingDataSource.getConnection();
    conn.setAutoCommit(true);

    System.out.println("Creating statement...");
    String sql = "insert into user (name) values(?)";
    final PreparedStatement pstmt = conn.prepareStatement(sql);

// Set the variables
    pstmt.setString(1, "Pappu");
// Add it to the batch
    pstmt.addBatch();

// Set the variables
    pstmt.setString(1, "Pawan");
// Add it to the batch
    pstmt.addBatch();

    //Create an int[] to hold returned values
    int[] count = pstmt.executeBatch();
    System.out.println(Arrays.toString(count));
  }

}
