package jdbc.java.account.alg;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatUtils {

  /**
   *
   */
  public static String keepFigures(int number, int mod) {
    return String.format("%0" + mod + "d", number);
  }

  public static String keepFigures(long number, int mod) {
    return String.format("%0" + mod + "d", number);
  }

  public static String formatDate(Date date, String format) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
    return simpleDateFormat.format(date);
  }

  /**
   * hash 保留两位小数
   *
   * @param obj
   * @return
   */
  public static String hashUpper(Object obj, int mod) {
    int hash = 0;
    if (obj != null) {
      hash = String.valueOf(obj).toUpperCase().hashCode();
    }

    int rs = hash % mod;
    return rs > 9 ? String.valueOf(rs) : "0" + rs;
  }

  public static void main(String[] args) {
    System.out.println(keepFigures(1, 2));
    System.out.println(keepFigures(1, 3));
    System.out.println(keepFigures(10, 3));
    System.out.println(keepFigures(10, 1));
    System.out.println(formatDate(new Date(), "yyyyMM"));
  }
}
