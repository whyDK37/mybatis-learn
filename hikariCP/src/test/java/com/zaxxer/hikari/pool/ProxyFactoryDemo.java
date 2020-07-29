package com.zaxxer.hikari.pool;

import com.zaxxer.hikari.util.FastList;
import java.sql.Connection;
import java.sql.Statement;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProxyFactoryDemo {

  @Test
  void main() {
    final Connection connection = Mockito.mock(Connection.class);
    PoolEntry entry = new PoolEntry(connection, null, false, false);
    FastList<Statement> fastList = new FastList<>(Statement.class);

    final ProxyConnection proxyConnection = ProxyFactory
        .getProxyConnection(entry, connection,
            fastList, null, System.currentTimeMillis(), false, false);

    System.out.println(proxyConnection);
  }
}
