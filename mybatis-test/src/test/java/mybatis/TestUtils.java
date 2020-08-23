package mybatis;

import java.util.Arrays;
import java.util.List;
import org.apache.ibatis.executor.BatchResult;

public class TestUtils {


  public static void printBatchResult(List<BatchResult> batchResults) {
    for (int i = 0; i < batchResults.size(); i++) {
      System.out.println(String.format("第 %d 个结果", i + 1));
      BatchResult batchResult = batchResults.get(i);
      System.out.println(Arrays.toString(batchResult.getUpdateCounts()));
    }
  }

}
