package mybatis;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.XMLScriptBuilder;
import org.apache.ibatis.session.Configuration;

public class XPathParserTest {

  public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
    XPathParser parser = new XPathParser("" +
        "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
        "<mapper namespace=\"com.mmc.price.dal.mapper.PriceRuleConfigDOMapperExt\" >\n" +
        "\n" +
        "    <select id=\"where\"\n" +
        "            resultMap=\"BaseResultMap\">\n" +
        "and scene_code = 'settlePriceSubmitCheck' and scene_type = '${sceneType}' and merchant_code = '#{merchantCode}'"
        +
//        "and is_deleted = 0 and scene_code = 'priceSaleRDCTR' and merchant_code = '${merchentCode}' and (org_no = '${orgNo}' or org_no = 'ALL') and (forest_cat_id3 = ${forestCatId3}) and start_time &lt;= '${startTime}' order by org_no desc, start_time desc limit 1 " +
        "    </select>" +
        "</mapper>");
    XNode mapper = parser.evalNode("/mapper");
    List<XNode> xNodes = mapper.evalNodes("select");

    Map<String, Object> params = new HashMap<>();
    params.put("merchantCode", "CSMMC");
    params.put("orgNo", "HMYXAHA");
    params.put("forestCatId3", 201683903L);
    params.put("sceneType", "sceneType");
    params.put("startTime", new Date());

    Configuration configuration = new Configuration();
    XMLScriptBuilder builder = new XMLScriptBuilder(configuration, xNodes.get(0),
        params.getClass());
    SqlSource sqlSourceRaw = builder.parseScriptNode();

    // detail
    Field rootSqlNodeField = sqlSourceRaw.getClass().getDeclaredField("rootSqlNode");
    rootSqlNodeField.setAccessible(true);
    SqlNode rootSqlNode = (SqlNode) rootSqlNodeField.get(sqlSourceRaw);
    DynamicContext context = new DynamicContext(configuration, params);
    rootSqlNode.apply(context);
    // 这时，sql已经替换了 ${} 并且已经把满足条件的 #{} 拼上了
    System.out.println("context.getSql() = " + context.getSql());
    SqlSourceBuilder sqlSourceParser = new SqlSourceBuilder(configuration);
    Class<?> parameterType = params == null ? Object.class : params.getClass();
    SqlSource sqlSource = sqlSourceParser.parse(context.getSql(), parameterType,
        context.getBindings());
    BoundSql boundSql = sqlSource.getBoundSql(params);
    context.getBindings().forEach(boundSql::setAdditionalParameter);

    // one call
    System.out.println(sqlSourceRaw.getBoundSql(params));

  }

}
