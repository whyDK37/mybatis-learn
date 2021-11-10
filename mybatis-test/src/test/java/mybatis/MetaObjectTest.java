package mybatis;

import example.domain.Address;
import example.domain.User;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.junit.jupiter.api.Test;

class MetaObjectTest {

  protected ReflectorFactory reflectorFactory = new DefaultReflectorFactory();
  protected ObjectFactory objectFactory = new DefaultObjectFactory();
  protected ObjectWrapperFactory objectWrapperFactory = new DefaultObjectWrapperFactory();

  @Test
  void test() {
    User user = new User();
    user.setAddress("bj");
    user.setName("why");

    MetaObject metaObject = MetaObject
        .forObject(user, objectFactory, objectWrapperFactory, reflectorFactory);
    System.out.println(metaObject.getGetterType("name"));
    System.out.println(metaObject.getValue("name"));
    System.out.println(metaObject.getSetterType("address"));
    System.out.println(metaObject.getValue("address"));
    System.out.println(metaObject.getSetterType("address.city"));
    System.out.println(metaObject.getValue("address.city"));
  }

}
