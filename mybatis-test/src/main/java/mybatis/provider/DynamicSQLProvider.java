package mybatis.provider;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import example.domain.QueryParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicSQLProvider {

    public String query2(QueryParam queryParam, String code) {
        return null;
    }

    public String query(QueryParam queryParam, ProviderContext context) {

        String tableName = getTableNameByMs(context);
//        String querySql = getQueryColumns(queryParam, queryParam.getClass());
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(StringUtils.join(getColumnsByMs(context), ","));
        sql.append(" FROM ");
        sql.append(tableName);
        sql.append(" WHERE 1=1");
//        sql.append(querySql);
//        sql.append(getDeleteFilterSql(ms));
        if (StringUtils.isNotEmpty(queryParam.getGroupBy())) {
            // ������group by�ֶ�
            sql.append(" GROUP BY " + queryParam.getGroupBy());
        }
        if (StringUtils.isNotEmpty(queryParam.getOrderBy())) {
            // ������order by�ֶ�
            sql.append(" ORDER BY " + queryParam.getOrderBy() + " " + queryParam.getOrderDirection());
        }
        // ������ͨquery���Զ����� LIMIT 10000 �Է�OOM
        sql.append(" LIMIT 10000");
        return sql.toString();
    }


    private String getTableNameByMs(ProviderContext context) {
//        String id = context.getDatabaseId();
//        String className = id.substring(0, id.lastIndexOf("."));
        Class<?> s = context.getMapperType();//getClass(className, ms);
        TableName table = s.getAnnotation(TableName.class);
        if (table != null && StringUtils.isNotEmpty(table.value())) {
            return table.value();
        }

        // ��ȡ������"."֮�������
//        int lastDotIndex = 0;
//        int secondLastDotIndex = 0;

//        for (int i = id.length() - 1; i >= 0; i--) {
//            if (id.charAt(i) != '.') {
//                continue;
//            }
//            if (lastDotIndex == 0) {
//                lastDotIndex = i;
//            } else {
//                secondLastDotIndex = i;
//                break;
//            }
//        }
//        String mapperName = id.substring(secondLastDotIndex + 1, lastDotIndex);
//        if (mapperName.endsWith("Mapper")) {
//            String tableCamel = mapperName.substring(0, mapperName.length() - "Mapper".length());
//            String tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, tableCamel);
//            return tableName;
//        } else if (mapperName.endsWith("Dao") || mapperName.endsWith("DAO")) {
//            String tableCamel = mapperName.substring(0, mapperName.length() - "DAO".length());
//            String tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, tableCamel);
//            return tableName;
//        }
        throw new RuntimeException("Mapper class name must end with 'Mapper' or 'DAO' or 'Dao'");
    }


    private List<String> getColumnsByMs(ProviderContext context) {
//        String id = context.getDatabaseId();
//        String className = id.substring(0, id.lastIndexOf("."));
//        Class<?> s = context.getMapperType();//getClass(className, ms);
//        Type[] genType = s.getGenericInterfaces();
//        Type[] params = ((ParameterizedType) genType[0]).getActualTypeArguments();
//        Class<?> dataObjectClass = (Class<?>) params[1];
//        List<String> rawColumns = getColumns(dataObjectClass);
//        List<String> resultColumns = getMappedColumnName(dataObjectClass, rawColumns);
        return Lists.newArrayList("*");
    }


    private List<String> getColumns(Class<?> paramClass) {
        Method[] methods = paramClass.getMethods();
        List<String> result = new ArrayList<String>();
        for (Method method : methods) {
            if (method.getDeclaringClass().equals(Object.class)) {
                continue;
            }
            if (method.getName().startsWith("get")) {
                String column = method.getName().substring(3);
                result.add(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, column));
            }
        }
        return result;
    }


    private List<String> getMappedColumnName(Class<?> clazz, List<String> oldUnderScoreName) {
        Map<String/*attr name*/, String/*db column*/> map = new HashMap<String, String>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            ColumnName c = field.getAnnotation(ColumnName.class);
            if (c != null && StringUtils.isNotEmpty(c.value()) && !ColumnName.IGNORE.equals(c.value())) {
                map.put(field.getName(), c.value());
            }
        }
        List<String> resultList = new ArrayList<String>();
        for (String string : oldUnderScoreName) {
            String fieldName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, string);
            String columnFromAnnotation = map.get(fieldName);
            if (columnFromAnnotation == null) {
                resultList.add(string);
            } else {
                resultList.add(columnFromAnnotation);
            }
        }
        return resultList;
    }


    private Class<?> getClass(String className, MappedStatement ms) {
        Configuration config = ms.getConfiguration();
        try {
            return Class.forName(className, true, config.getClass().getClassLoader());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
