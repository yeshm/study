package study.yeshm.autoconfigure

import groovy.transform.CompileStatic
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.jdbc.JdbcProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import study.yeshm.DemoTransactionManager

import javax.sql.DataSource

@CompileStatic
@Configuration
class DemoAutoConfiguration {

    private final DataSource dataSource

    private final JdbcProperties properties

    DemoAutoConfiguration(DataSource dataSource, JdbcProperties properties) {
        this.dataSource = dataSource
        this.properties = properties
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean(DemoTransactionManager.class)
    DemoTransactionManager demoTransactionManager() {
        DemoTransactionManager transactionManager = new DemoTransactionManager(this.dataSource)

        return transactionManager
    }

}
