package jdbc.java.account.alg;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

public class TableShardingAlgorithm implements PreciseShardingAlgorithm<String>,
    ComplexKeysShardingAlgorithm<String> {

  public TableShardingAlgorithm() {
    System.out.println();
  }

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

  @Override
  public Collection<String> doSharding(Collection<String> availableTargetNames,
      ComplexKeysShardingValue<String> shardingValue) {
    String tb_name = shardingValue.getLogicTableName() + "_";
    String ym = new SimpleDateFormat("yyyyMM")
        .format(shardingValue.getColumnNameAndRangeValuesMap().get("exp_date"));
    tb_name = tb_name + ym;
    System.out.println("tb_name:" + tb_name);

    return Collections.singleton(tb_name);
  }
}