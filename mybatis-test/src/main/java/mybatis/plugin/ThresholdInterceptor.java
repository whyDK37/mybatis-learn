package mybatis.plugin;

import java.sql.Statement;
import java.util.Properties;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.jdbc.PreparedStatementLogger;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;

/**
 * 官方
 * @see StatementHandler
 */
@Intercepts({
    @Signature(type = StatementHandler.class, method = "query", args = {Statement.class,
        ResultHandler.class})
})
//实现Interceptor接口
public class ThresholdInterceptor implements Interceptor {

  private long slowSqlTime;

  /**
   * 处理业务逻辑的方法
   */
  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    long start = System.currentTimeMillis();
    //之前拦截方法，即query那个方法
    Object object = invocation.proceed();
    long end = System.currentTimeMillis();
    long countTime = end - start;
    if (countTime >= slowSqlTime) {
      //获取PreparedStatementLogger代理类，mybatis日志功能通过动态代理把PreparedStatement包装成了PreparedStatementLogger
      Object[] args = invocation.getArgs();
      Statement statement = (Statement) args[0];
      //通过反射得到目标对象
      MetaObject metaObjectStat = SystemMetaObject.forObject(statement);
      //拿到代理对象实例
      PreparedStatementLogger statementLogger = (PreparedStatementLogger) metaObjectStat
          .getValue("h");
      Statement preparedStatement = statementLogger.getPreparedStatement();
      System.out
          .println("当前sql：" + preparedStatement.toString() + "，执行时间为：" + countTime + "毫秒，监控为慢sql！");
    }
    return object;
  }

  @Override
  public Object plugin(Object target) {
    return Plugin.wrap(target, this);
  }

  @Override
  public void setProperties(Properties properties) {
    this.slowSqlTime = Long.parseLong(properties.getProperty("slowSqlTime"));
  }
}