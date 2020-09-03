# mybatis 3XX

- 官方网站  https://mybatis.org/mybatis-3/
- 插件https://mybatis.org/mybatis-3/configuration.html#plugins

## $ # 的区别
```
#{} 在动态解析的时候， 会解析成一个参数标记符。就是解析之后的语句是：
select * from user where name = ？; 

${}在动态解析的时候，会将我们传入的参数当做String字符串填充到我们的语句中，就会变成下面的语句
select * from user where name = "dato"; 
```
## 配置
### insert
获取自增主键
useGeneratedKeys="true" keyProperty="id"
## 执行器体系
- 流程
sqlSession -> Executor -> statementHandler -> DB

## 排忧解难

### parameterType
3.2.4 版本之后，启用了  parameterType 参数，如果与参数类型不一致，会抛异常。
https://mp.weixin.qq.com/s/sk0Kou9V727tRe5wddmDig


# shardingJDBC

https://www.bookstack.cn/read/ShardingSphere-4.x-zh/f102561b8187d169.md



