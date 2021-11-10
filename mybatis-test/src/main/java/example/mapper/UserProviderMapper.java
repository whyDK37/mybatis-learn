package example.mapper;

import example.domain.QueryParam;
import example.domain.User;
import mybatis.provider.DynamicSQLProvider;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author why
 */
public interface UserProviderMapper {

    @SelectProvider(type = DynamicSQLProvider.class, method = "query")
    List<User> query(QueryParam queryParam);

    @SelectProvider(type = DynamicSQLProvider.class, method = "query2")
    List<User> query2(QueryParam queryParam, String code);
}
