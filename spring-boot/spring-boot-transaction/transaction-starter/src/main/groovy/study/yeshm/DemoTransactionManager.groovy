package study.yeshm

import org.springframework.jdbc.datasource.DataSourceTransactionManager

import javax.sql.DataSource

class DemoTransactionManager extends DataSourceTransactionManager {
    DemoTransactionManager(DataSource dataSource) {
        super(dataSource)
    }
}
