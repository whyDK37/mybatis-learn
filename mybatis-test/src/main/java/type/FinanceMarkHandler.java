package type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * finance mark 数据存储压缩空间
 *
 * @author wanghuanyu10
 */
public class FinanceMarkHandler extends BaseTypeHandler<String> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setString(i, trans2ShortStr(parameter));
  }

  @Override
  public String getNullableResult(ResultSet rs, String columnName)
      throws SQLException {
    return shortStr2Origin(rs.getString(columnName));
  }

  @Override
  public String getNullableResult(ResultSet rs, int columnIndex)
      throws SQLException {
    return shortStr2Origin(rs.getString(columnIndex));
  }

  @Override
  public String getNullableResult(CallableStatement cs, int columnIndex)
      throws SQLException {
    return shortStr2Origin(cs.getString(columnIndex));
  }


  /**
   * 抓换为 int(hex)value; 的格式，如 11，表示标位2打标1
   *
   * @param financeMark
   * @return
   */
  public String trans2ShortStr(String financeMark) {
    if (StringUtils.isEmpty(financeMark)) {
      return "";
    }
    if (financeMark.contains(";")) {
      return financeMark;
    }

    StringBuilder sb = new StringBuilder();
    char[] financeMarkArray = financeMark.toCharArray();
    for (int i = 0; i < financeMarkArray.length; i++) {
      if (financeMarkArray[i] == '0') {
        continue;
      }
      sb.append(Integer.toHexString(i + 1)).append(financeMarkArray[i]).append(';');
    }
    return sb.toString();
  }


  /**
   * 抓换为 int(hex)value; 的格式，如 11，表示标位2打标1
   *
   * @param financeMark
   * @return
   */
  public String shortStr2Origin(String financeMark) {
    if (StringUtils.isEmpty(financeMark)) {
      return "";
    }

    if (!financeMark.contains(";")) {
      return financeMark;
    }

    // 转成原始的格式
    StringBuilder sb = new StringBuilder();
    char[] financeMarkArray = financeMark.toCharArray();
    for (int i = 0; i < financeMarkArray.length; i++) {
      if (financeMarkArray[i] == '0') {
        continue;
      }
      sb.append(Integer.toHexString(i + 1)).append(financeMarkArray[i]).append(';');
    }
    return sb.toString();
  }
}
