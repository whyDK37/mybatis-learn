//package shardingsphere;
//
//import javax.sql.DataSource;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@EnableTransactionManagement
//public class TransactionConfiguration {
//
//    @Bean
//    public PlatformTransactionManager txManager(final DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    @Bean
//    public JdbcTemplate jdbcTemplate(final DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
//}