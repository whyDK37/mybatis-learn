package jdbc.java;//package jdbc.java;

import java.util.Collection;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

public class ModuloShardingTableAlgorithm implements PreciseShardingAlgorithm {

  @Override
  public String doSharding(Collection availableTargetNames, PreciseShardingValue shardingValue) {
    System.out.println("availableTargetNames = " + availableTargetNames);
    System.out.println("shardingValue.getColumnName() = " + shardingValue.getColumnName());
    System.out.println("shardingValue.getValue() = " + shardingValue.getValue());
    return String.valueOf(availableTargetNames.iterator().next());
  }
}
