package jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class BatchExample {

  // JDBC driver name and database URL
  static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
  static final String DB_URL = "jdbc:mysql://localhost/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

  //  Database credentials
  static final String USER = "root";
  static final String PASS = "root";

  public static void main(String[] args) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    try {
      //STEP 2: Register JDBC driver
//      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      DatabaseMetaData metaData = conn.getMetaData();
      System.out.println("metaData.supportsBatchUpdates() = " + metaData.supportsBatchUpdates());

      //STEP 4: Execute a query
      System.out.println("Creating statement...");
      String sql = "update user set nameX=? where id = ?";
      pstmt = conn.prepareStatement(sql);

      conn.setAutoCommit(true);

// Set the variables
      pstmt.setString(1, "Pappu");
      pstmt.setInt(2, 1);
// Add it to the batch
      pstmt.addBatch();

// Set the variables
      pstmt.setString(1, "Pawan");
      pstmt.setInt(2, 2);
// Add it to the batch
      pstmt.addBatch();

      //Create an int[] to hold returned values
      int[] count = pstmt.executeBatch();
      System.out.println(Arrays.toString(count));

      pstmt.close();
      conn.close();
    } catch (Exception se) {
      //Handle errors for JDBC
      se.printStackTrace();
    }//Handle errors for Class.forName
    finally {
      //finally block used to close resources
      try {
        if (pstmt != null) {
          pstmt.close();
        }
      } catch (SQLException se2) {
      }// nothing we can do
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException se) {
        se.printStackTrace();
      }//end finally try
    }//end try
    System.out.println("\nGoodbye!");
  }//end main
}//end FirstExample