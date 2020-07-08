package jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class SelectExample {

  // JDBC driver name and database URL
  static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
  //useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&
  static final String DB_URL = "jdbc:mysql://localhost/test?tinyInt1isBit=true&serverTimezone=UTC";

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
      String sql = "select * from user where id = ?";
      pstmt = conn.prepareStatement(sql);

// Set the variables
      pstmt.setInt(1, 1);

      final ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        //Retrieve by column name
        final ResultSetMetaData rsMetaData = rs.getMetaData();
        System.out.println(rsMetaData.getColumnName(3));
        System.out.println(rsMetaData.getColumnClassName(3));
        System.out.println(rs.getInt(3));
        System.out.println(rs.getBoolean(3));
        System.out.println(rs.getByte(3));
      }
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