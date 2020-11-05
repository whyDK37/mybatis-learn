package hikari;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import java.lang.management.ManagementFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

public class DatasourceDemo {

  private static HikariPoolMXBean poolProxy;


  public static void main(String[] args)
      throws SQLException, MalformedObjectNameException {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl("jdbc:mysql://localhost:3306/test");
    config.setUsername("root");
    config.setPassword("root");
    config.addDataSourceProperty("serverTimezone", "UTC");
    config.setAllowPoolSuspension(true);
    config.setRegisterMbeans(true);
    // 获取连接超时时间
    config.setConnectionTimeout(1000);//30000
    config.setIdleTimeout(600000);
    config.setInitializationFailTimeout(1);
    config.setValidationTimeout(5000);
    HikariDataSource ds = new HikariDataSource(config);

    MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
    ObjectName poolName = new ObjectName("com.zaxxer.hikari:type=Pool (" + ds.getPoolName() + ")");
    poolProxy = JMX.newMXBeanProxy(mBeanServer, poolName, HikariPoolMXBean.class);

    ScheduledExecutorService scheduledExecutorService = Executors
        .newSingleThreadScheduledExecutor();
    scheduledExecutorService.scheduleAtFixedRate(() -> System.out.println("HikariPoolState = "
        + "Active=[" + poolProxy.getActiveConnections() + "] "
        + "Idle=[" + poolProxy.getIdleConnections() + "] "
        + "Wait=[" + poolProxy.getThreadsAwaitingConnection() + "] "
        + "Total=[" + poolProxy.getTotalConnections() + "]"), 10, 1000, TimeUnit.MICROSECONDS);

    Connection conn = ds.getConnection();
    Statement sm = conn.createStatement();
    ResultSet rs = null;
    for (int i = 0; i < 10; i++) {
      rs = sm.executeQuery("select 1");
    }
    rs.close();
    sm.close();
    conn.close();
    ds.close();
  }

}
