package mybatis.mapper;

import mybatis.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

  @Select("SELECT * FROM user WHERE id = #{id}")
  User findById(@Param("id") Integer id);

  @Update("update user set name = #{name} WHERE id = #{id}")
  void updateUser(@Param("id") long id, @Param("name") String c);
}