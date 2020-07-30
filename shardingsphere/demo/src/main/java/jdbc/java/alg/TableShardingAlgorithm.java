package jdbc.java.alg;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

public class TableShardingAlgorithm implements PreciseShardingAlgorithm<String> {

  @Override
  public String doSharding(Collection<String> collection,
      PreciseShardingValue<String> preciseShardingValue) {
    String tb_name = preciseShardingValue.getLogicTableName() + "_";
    String ym = new SimpleDateFormat("yyyyMM")
        .format(preciseShardingValue.getValue());
    tb_name = tb_name + ym;
    System.out.println("tb_name:" + tb_name);

    return tb_name;
//    for (String each : collection) {
//      System.out.println("t_order_:" + each);
//      if (each.equals(tb_name)) {
//        return each;
//      }
//    }

//    throw new IllegalArgumentException();

  }

}