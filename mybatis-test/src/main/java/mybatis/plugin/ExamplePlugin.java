package mybatis.plugin;

import java.util.Properties;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;

//@Intercepts({})
//public class ExamplePlugin implements Interceptor {
//
//  private Log log = LogFactory.getLog(ExamplePlugin.class);
//
//  @Override
//  public Object intercept(Invocation invocation) throws Throwable {
//    log.debug("example plugin...");
//    return invocation.proceed();
//  }
//
//  @Override
//  public Object plugin(Object target) {
//    return Plugin.wrap(target, this);
//  }
//
//}
