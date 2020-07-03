package mybatis;

import example.mapper.User;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.executor.BatchExecutor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.SimpleExecutor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * https://mp.weixin.qq.com/s/sk0Kou9V727tRe5wddmDig
 * <p>
 * <p>3.2.4 版本之后，启用了  parameterType 参数，如果与参数类型不一致，会抛异常。
 */
class ExecutorTest {

  SqlSessionFactory sessionFactory;

  JdbcTransaction jdbcTransaction;

  Configuration configuration;

  @BeforeEach
  void before() throws IOException {

    //使用MyBatis提供的Resources类加载mybatis的配置文件
    Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
    //构建sqlSession的工厂
    sessionFactory = new SqlSessionFactoryBuilder().build(reader);

    configuration = sessionFactory.getConfiguration();
    jdbcTransaction = new JdbcTransaction(sessionFactory.openSession().getConnection());
  }

  @Test
  public void simple() throws SQLException {

    SimpleExecutor simpleExecutor = new SimpleExecutor(sessionFactory.getConfiguration(),
        jdbcTransaction);

    final MappedStatement ms = sessionFactory.getConfiguration()
        .getMappedStatement("example.mapper.UserMapper.getUserByID");
    final BoundSql boundSql = ms.getBoundSql(1);

    simpleExecutor.doQuery(ms, 1, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER, boundSql);

  }


  @Test
  void batch() throws SQLException {

    BatchExecutor batchExecutor = new BatchExecutor(configuration, jdbcTransaction);

    final MappedStatement ms = configuration
        .getMappedStatement("example.mapper.UserMapper.updateName");

    User user = new User();
    user.setId(2);
    user.setName("" + new Date());

    batchExecutor.doUpdate(ms, user);
    batchExecutor.doUpdate(ms, user);

    //
    final List<BatchResult> batchResults = batchExecutor.flushStatements(false);
    for (BatchResult batchResult : batchResults) {
      System.out.println(Arrays.toString(batchResult.getUpdateCounts()));
    }
  }

}
