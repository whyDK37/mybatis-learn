//package shardingsphere;
//
//import javax.annotation.PostConstruct;
//import javax.sql.DataSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;
//
//// shardingshpere分布式事务
//@SpringBootApplication(exclude = {JtaAutoConfiguration.class})
//public class ShardingsphereApplication {
//
//  public static void main(String[] args) {
//    SpringApplication.run(ShardingsphereApplication.class, args);
//  }
//
////  @Autowired
////  DataSource dataSource;
////
////  @PostConstruct
////  public void post() {
////    System.out.println(dataSource);
////  }
//
//}
//
