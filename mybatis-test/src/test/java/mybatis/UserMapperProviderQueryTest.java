package mybatis;

import example.domain.QueryParam;
import example.domain.User;
import example.mapper.UserProviderMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.List;

/**
 * https://mp.weixin.qq.com/s/sk0Kou9V727tRe5wddmDig
 * <p>
 * <p>3.2.4 版本之后，启用了  parameterType 参数，如果与参数类型不一致，会抛异常。
 */
class UserMapperProviderQueryTest {

    SqlSessionFactory sessionFactory;

    JdbcTransaction jdbcTransaction;

    Configuration configuration;

    @BeforeEach
    void before() throws IOException {

        //使用MyBatis提供的Resources类加载mybatis的配置文件
        Reader reader = Resources.getResourceAsReader("mybatis-provider-config.xml");
        //构建sqlSession的工厂
        sessionFactory = new SqlSessionFactoryBuilder().build(reader);

        configuration = sessionFactory.getConfiguration();
        jdbcTransaction = new JdbcTransaction(sessionFactory.openSession().getConnection());
    }

    /**
     * 插入后获取不到id
     *
     * @throws SQLException
     */
    @Test
    void query() throws SQLException {

        SqlSession sqlSession = sessionFactory.openSession(true);
        UserProviderMapper mapper = sqlSession.getMapper(UserProviderMapper.class);

        List<User> users = mapper.query2(new QueryParam(), "a");

        for (User user : users) {
            System.out.println(user);
        }


    }

}
