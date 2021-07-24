package example.mapper;

import example.domain.QueryParam;
import example.domain.User;
import mybatis.provider.DynamicSQLProvider;
import mybatis.provider.TableName;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * @author why
 */
@TableName("user")
public interface UserMapper {

    User getUserByID(Integer id);

    Map<String, Object> getMapByID(Integer id);


    User getUserByIDAndName(@Param("id") Integer id, @Param("name") String name);

    List<User> getAll();

    // 封装map使用哪个属性
    @MapKey("id")
//  @Select("select * from user")
    Map<Integer, User> getAllMap();

    void updateName(User user);

    int insertUser(User user);

    int insertUsers(List<User> user);

    int insertUserUseSelectKey(User user);

    int insertUsersUseSelectKey(List<User> user);

    int deleteById(User user);

    @SelectProvider(type = DynamicSQLProvider.class, method = "query")
    List<User> query(QueryParam queryParam);
}
