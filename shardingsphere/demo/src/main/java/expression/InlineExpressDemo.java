package expression;

import java.util.List;
import org.apache.shardingsphere.underlying.common.config.inline.InlineExpressionParser;

public class InlineExpressDemo {

  public static void main(String[] args) {
    String expression = "ds_$->{2019..2020}.trans_account_$->{2019..2020}$->{['01','02','03','04','05','06','07','08','09','10','11','12']}";
    String expression2 = "ds_$->{2019..2020}.trans_account_$->{2019..2020}$->{0...12}";


    List<String> strings = new InlineExpressionParser(
        expression).splitAndEvaluate();
    for (String string : strings) {
      System.out.println(string);
    }
  }

}
