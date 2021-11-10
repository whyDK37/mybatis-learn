package example.mapper;

import example.domain.User;
import example.domain.UserExampleExt;
import example.domain.UserKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapperExample {
    long countByExample(UserExampleExt example);

    int deleteByExample(UserExampleExt example);

    int deleteByPrimaryKey(UserKey key);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExampleExt example);

    User selectByPrimaryKey(UserKey key);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExampleExt example);

    int updateByExample(@Param("record") User record, @Param("example") UserExampleExt example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}