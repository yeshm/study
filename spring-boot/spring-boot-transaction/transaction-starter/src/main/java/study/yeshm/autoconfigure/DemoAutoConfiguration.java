package study.yeshm.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.JdbcProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import study.yeshm.DemoTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DemoAutoConfiguration {

    private final DataSource dataSource;

    private final JdbcProperties properties;

    DemoAutoConfiguration(DataSource dataSource, JdbcProperties properties) {
        this.dataSource = dataSource;
        this.properties = properties;
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean(DemoTransactionManager.class)
    public DemoTransactionManager demoTransactionManager() {
        DemoTransactionManager transactionManager = new DemoTransactionManager(this.dataSource);

        return transactionManager;

    }

}
