//package mybatis.plugin;
//
//import java.sql.Connection;
//import java.util.Properties;
//import java.util.StringTokenizer;
//import org.apache.ibatis.executor.statement.StatementHandler;
//import org.apache.ibatis.mapping.BoundSql;
//import org.apache.ibatis.plugin.Interceptor;
//import org.apache.ibatis.plugin.Intercepts;
//import org.apache.ibatis.plugin.Invocation;
//import org.apache.ibatis.plugin.Plugin;
//import org.apache.ibatis.plugin.Signature;
//import org.apache.ibatis.reflection.MetaObject;
//import org.apache.ibatis.reflection.SystemMetaObject;
//import org.mybatis.logging.Logger;
//import org.mybatis.logging.LoggerFactory;
// 分表插件
//@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
//public class TableSplitInterceptor implements Interceptor {
//
//    private static Logger logger = LoggerFactory.getLogger(TableSplitInterceptor.class);
//    private static String sqlName = "delegate.boundSql.sql";
//    private Properties properties;
//
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
//        MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
//
////        BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
//        // Configuration configuration = (Configuration) metaStatementHandler
//        // .getValue("delegate.configuration");
//        Object parameterObject = metaStatementHandler.getValue("delegate.boundSql.parameterObject");
//        TableSplitInterceptor.doSplitTable(metaStatementHandler, parameterObject);
//        // 传递给下一个拦截器处理
//        return invocation.proceed();
//
//    }
//
//    @Override
//    public Object plugin(Object target) {
//        // 当目标类是StatementHandler类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的次数
//        if (target instanceof StatementHandler) {
//            return Plugin.wrap(target, this);
//        } else {
//            return target;
//        }
//    }
//
//    @Override
//    public void setProperties(Properties properties) {
//        this.properties = properties;
//    }
//
//    public static void doSplitTable(MetaObject metaStatementHandler, Object parameterObject) {
//        String originalSql = ((String) metaStatementHandler.getValue(sqlName));
//        if (!StringUtils.isEmpty(originalSql)) {
////            MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
////            String id = mappedStatement.getId();
////            String className = id.substring(0, id.lastIndexOf("."));
////            Class<?> classObj = Class.forName(className);
//            // 根据配置自动生成分表SQL
//            Class<?> aClass = parameterObject.getClass();
//
//            // List 参数
//            if (parameterObject instanceof Map) {
//                Map param = (Map) parameterObject;
//                if (param.containsKey("list")) {
//                    Collection collection = (Collection) param.get("list");
//                    Object o = collection.iterator().next();
//
//                    parameterObject = o;
//                    aClass = o  .getClass();
//                }
//            }
//
//            TableSplit tableSplit = aClass.getAnnotation(TableSplit.class);
//            if (tableSplit != null && tableSplit.split()) {
//                if (logger.isDebugEnabled()) {
//                    logger.debug("分表前的SQL：{}", removeBreakingWhitespace(originalSql));
//                }
//                Strategy strategy = StrategyManager.getStrategy(tableSplit.strategy());//获取分表策略来处理分表
//                if (strategy == null) {
//                    throw new RuntimeException("TableSplit strategy[" + tableSplit.strategy() + "] is not exist, please check StrategyManager.");
//                }
//                String convertedSql = strategy.convert(originalSql, tableSplit, parameterObject);
//                metaStatementHandler.setValue(sqlName, convertedSql);
//                if (logger.isDebugEnabled()) {
//                    logger.debug("分表后的SQL：{}", removeBreakingWhitespace(convertedSql));
//                }
//            }
//
//        }
//    }
//
//    private static String removeBreakingWhitespace(String original) {
//        StringTokenizer whitespaceStripper = new StringTokenizer(original);
//        StringBuilder builder = new StringBuilder();
//        while (whitespaceStripper.hasMoreTokens()) {
//            builder.append(whitespaceStripper.nextToken());
//            builder.append(" ");
//        }
//        return builder.toString();
//    }
//
//
//    public static void doSplitTable(BoundSql boundSql, Object parameterObject) {
//        String originalSql = boundSql.getSql();
//        if (!StringUtils.isEmpty(originalSql)) {
////            MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
////            String id = mappedStatement.getId();
////            String className = id.substring(0, id.lastIndexOf("."));
////            Class<?> classObj = Class.forName(className);
//            // 根据配置自动生成分表SQL
//            Class<?> aClass = parameterObject.getClass();
//
//            // List 参数
//            if (parameterObject instanceof Map) {
//                Map param = (Map) parameterObject;
//                if (param.containsKey("list")) {
//                    Collection collection = (Collection) param.get("collection");
//                    Object o = collection.iterator().next();
//
//                    parameterObject = o;
//                    aClass = o.getClass();
//                }
//            }
//
//            TableSplit tableSplit = aClass.getAnnotation(TableSplit.class);
//            if (tableSplit != null && tableSplit.split()) {
//                logger.debug("分表前的SQL：{}", originalSql);
//                Strategy strategy = StrategyManager.getStrategy(tableSplit.strategy());//获取分表策略来处理分表
//                if (strategy == null) {
//                    throw new RuntimeException("TableSplit strategy[" + tableSplit.strategy() + "] is not exist, please check StrategyManager.");
//                }
//                String convertedSql = strategy.convert(originalSql, tableSplit, parameterObject);
//                ReflectionUtils.setFieldValue(boundSql, "sql", convertedSql);
//                logger.debug("分表后的SQL：{}", convertedSql);
//            }
//
//        }
//    }
//}