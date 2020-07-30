package jdbc.java.alg;

import java.util.Collection;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;


public class DatabaseShardingAlgorithm implements PreciseShardingAlgorithm<String> {

  @Override
  public String doSharding(Collection<String> collection,
      PreciseShardingValue<String> preciseShardingValue) {

    String db_name = "lbs_sharding_";

    final int i = Math.abs(preciseShardingValue.getValue().hashCode()) % 4;
    if (i > 9) {
      db_name = db_name + i;
    } else {
      db_name = db_name + "0" + i;
    }
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

}