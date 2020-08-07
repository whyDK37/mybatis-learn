package jdbc.java.account.alg;

import java.util.Collection;
import java.util.Collections;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;


public class DatabaseShardingAlgorithm implements PreciseShardingAlgorithm<String>,
    ComplexKeysShardingAlgorithm<String> {

  public DatabaseShardingAlgorithm(){
    System.out.println();
  }

  @Override
  public String doSharding(Collection<String> collection,
      PreciseShardingValue<String> preciseShardingValue) {

    String db_name = "ds";

    final int i = Math.abs(preciseShardingValue.getValue().hashCode()) % 2;
//    if (i > 9) {
//      db_name = db_name + i;
//    } else {
//      db_name = db_name + "0" + i;
//    }
    db_name = db_name + i;
    System.out.println("db_name:" + db_name);
    return db_name;
//    for (String each : collection) {
//      System.out.println("db:" + each);
//      if (each.equals(db_name)) {
//        return each;
//      }
//    }
//
//    throw new IllegalArgumentException();
  }

  @Override
  public Collection<String> doSharding(Collection<String> availableTargetNames,
      ComplexKeysShardingValue<String> shardingValue) {

    String db_name = "ds";

    final int i = Math.abs(shardingValue.getColumnNameAndShardingValuesMap().get("seller_no").hashCode()) % 2;
//    if (i > 9) {
//      db_name = db_name + i;
//    } else {
//      db_name = db_name + "0" + i;
//    }
    db_name = db_name + i;
    System.out.println("db_name:" + db_name);
    return Collections.singleton(db_name);
  }
}