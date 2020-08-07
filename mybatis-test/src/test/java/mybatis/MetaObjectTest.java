package mybatis;

import example.mapper.User;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.junit.jupiter.api.Test;

public class MetaObjectTest {

  protected ReflectorFactory reflectorFactory = new DefaultReflectorFactory();
  protected ObjectFactory objectFactory = new DefaultObjectFactory();
  protected ObjectWrapperFactory objectWrapperFactory = new DefaultObjectWrapperFactory();

  @Test
  public void test() {
    MetaObject metaObject = MetaObject
        .forObject(new User(), objectFactory, objectWrapperFactory, reflectorFactory);
    System.out.println(metaObject.getGetterType("name"));
    System.out.println(metaObject.getSetterType("name"));
  }

}
