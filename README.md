# mybatis
mybatis3.3.0 中文注释并附带图解架构

官方网站  https://mybatis.org/mybatis-3/configuration.html#plugins

插件：https://mybatis.org/mybatis-3/configuration.html#plugins
# $ # 的区别
```
#{} 在动态解析的时候， 会解析成一个参数标记符。就是解析之后的语句是：
select * from user where name = ？; 

${}在动态解析的时候，会将我们传入的参数当做String字符串填充到我们的语句中，就会变成下面的语句
select * from user where name = "dato"; 
```

# 执行器体系
- 流程
sqlSession -> Executor -> statementHandler -> DB



