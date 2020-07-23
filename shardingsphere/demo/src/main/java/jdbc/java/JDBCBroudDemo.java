package jdbc.java;//package jdbc.java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import javax.sql.DataSource;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.apache.shardingsphere.transaction.core.TransactionTypeHolder;

public class JDBCBroudDemo {

  public static void main(String[] args) throws SQLException {
    JavaConfig javaConfig = new JavaConfig();
    final DataSource shardingDataSource = javaConfig.getShardingDataSource();

    String sql = "insert into t_config (name) values(?)";
    TransactionTypeHolder.set(TransactionType.XA);
    try (final Connection conn = shardingDataSource.getConnection()) {
      conn.setAutoCommit(false);

      System.out.println("Creating statement...");
      final PreparedStatement pstmt = conn.prepareStatement(sql);

// Set the variables
      pstmt.setString(1, "Pappu" + System.currentTimeMillis());
// Add it to the batch
      pstmt.addBatch();

      //Create an int[] to hold returned values
      int[] count = pstmt.executeBatch();
      System.out.println(Arrays.toString(count));
      conn.commit();
    }
  }

}
