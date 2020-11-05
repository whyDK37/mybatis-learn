package jdbc;

import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import java.sql.Connection;
import java.sql.Statement;
import javax.sql.XAConnection;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

class MyXid implements Xid {

  public int formatId;
  public byte gtrid[];
  public byte bqual[];

  public MyXid() {
  }

  public MyXid(int formatId, byte gtrid[], byte bqual[]) {
    this.formatId = formatId;
    this.gtrid = gtrid;
    this.bqual = bqual;
  }

  @Override
  public int getFormatId() {
    return formatId;
  }

  @Override
  public byte[] getBranchQualifier() {
    return bqual;
  }

  @Override
  public byte[] getGlobalTransactionId() {
    return gtrid;
  }
}

public class XADemo {

  public static MysqlXADataSource GetDataSource(
      String connString,
      String user,
      String passwd) {
    try {
      MysqlXADataSource ds = new MysqlXADataSource();
      ds.setUrl(connString);
      ds.setUser(user);
      ds.setPassword(passwd);
      return ds;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static void main(String[] args) {
    String connString1 = "jdbc:mysql://localhost:3306/ds1";
    String connString2 = "jdbc:mysql://localhost:3306/ds2 ";
    try {
      MysqlXADataSource ds1 =
          GetDataSource(connString1, "root", "root");
      MysqlXADataSource ds2 =
          GetDataSource(connString2, "root", "root");
      XAConnection xaConn1 = ds1.getXAConnection();
      XAResource xaRes1 = xaConn1.getXAResource();
      Connection conn1 = xaConn1.getConnection();
      Statement stmt1 = conn1.createStatement();
      XAConnection xaConn2 = ds2.getXAConnection();
      XAResource xaRes2 = xaConn2.getXAResource();
      Connection conn2 = xaConn2.getConnection();
      Statement stmt2 = conn2.createStatement();
      Xid xid1 = new MyXid(
          100,
          new byte[]{0x01},
          new byte[]{0x02});
      Xid xid2 = new MyXid(
          100,
          new byte[]{0x11},
          new byte[]{0x12});
      try {
        xaRes1.start(xid1, XAResource.TMNOFLAGS);
        stmt1.execute("UPDATE user SET name = 'why' WHERE id = 489497624278204417"
        );
        xaRes1.end(xid1, XAResource.TMSUCCESS);
        xaRes2.start(xid2, XAResource.TMNOFLAGS);
        stmt2.execute("UPDATE user SET name = 'why' WHERE id = 489497874426494980"
        );
        xaRes2.end(xid2, XAResource.TMSUCCESS);
        int ret2 = xaRes2.prepare(xid2);
        int ret1 = xaRes1.prepare(xid1);
        if (ret1 == XAResource.XA_OK
            && ret2 == XAResource.XA_OK) {
          xaRes1.commit(xid1, false);
          xaRes2.commit(xid2, false);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

