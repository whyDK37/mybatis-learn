package example.mapper;

/**
 * @author why
 */
public interface UserMapper {

  User getUserByID(Integer id);

  void updateName(User user);
}
