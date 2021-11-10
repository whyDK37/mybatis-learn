package mybatis;

import example.domain.User;
import example.mapper.ExtUserMapper;
import org.apache.ibatis.executor.BatchExecutor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static mybatis.TestUtils.printBatchResult;

/**
 * https://mp.weixin.qq.com/s/sk0Kou9V727tRe5wddmDig
 * <p>
 * <p>3.2.4 版本之后，启用了  parameterType 参数，如果与参数类型不一致，会抛异常。
 */
class UserMapperInsertTest {

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
    void batchInsert() throws SQLException {

        BatchExecutor batchExecutor = new BatchExecutor(configuration, jdbcTransaction);

        final MappedStatement insert = configuration
                .getMappedStatement("example.mapper.UserMapper.insertUser");

        User user = new User();
        user.setName("" + new Date());
        batchExecutor.doUpdate(insert, user);
        batchExecutor.doUpdate(insert, user);

        //
        final List<BatchResult> batchResults = batchExecutor.flushStatements(false);
        jdbcTransaction.commit();
        printBatchResult(batchResults);
    }

    @Test
    void batchInsertUseMapper() {

        SqlSession sqlSession = sessionFactory.openSession(ExecutorType.BATCH, false);
        ExtUserMapper mapper = sqlSession.getMapper(ExtUserMapper.class);

        List<User> users = createUsers("batch");

        mapper.insertUser(users.get(0));
        mapper.insertUser(users.get(1));

        sqlSession.commit();
        for (User user : users) {
            System.out.println(user.getId());
        }
    }


    @Test
    void insert() throws SQLException {

        SqlSession sqlSession = sessionFactory.openSession(true);
        ExtUserMapper mapper = sqlSession.getMapper(ExtUserMapper.class);

        User user = new User();
        user.setName("" + new Date());

        mapper.insertUser(user);
        System.out.println(user.getId());

    }

    @Test
    void insertUsers() throws SQLException {

        SqlSession sqlSession = sessionFactory.openSession(true);
        ExtUserMapper mapper = sqlSession.getMapper(ExtUserMapper.class);

        List<User> users = createUsers("a");

        mapper.insertUsers(users);
        for (User user : users) {
            System.out.println(user.getId());
        }

    }

    @Test
    void insertUserUseSelectKey() throws SQLException {

        SqlSession sqlSession = sessionFactory.openSession(true);
        ExtUserMapper mapper = sqlSession.getMapper(ExtUserMapper.class);

        User user = new User();
        user.setName("" + new Date());

        mapper.insertUserUseSelectKey(user);
        System.out.println(user.getId());

    }

    /**
     * 插入后获取不到id
     *
     * @throws SQLException
     */
    @Test
    void insertUsersUseSelectKey() throws SQLException {

        SqlSession sqlSession = sessionFactory.openSession(true);
        ExtUserMapper mapper = sqlSession.getMapper(ExtUserMapper.class);

        List<User> users = createUsers("a");

        mapper.insertUsersUseSelectKey(users);
        for (User user : users) {
            System.out.println(user.getId());
        }

    }

    private List<User> createUsers(String pre) {
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setName(pre + new Date());
        users.add(user1);
        user1 = new User();
        user1.setName(pre + new Date());
        users.add(user1);
        return users;
    }

}
