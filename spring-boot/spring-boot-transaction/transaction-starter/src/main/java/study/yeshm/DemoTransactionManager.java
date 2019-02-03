package study.yeshm;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

public class DemoTransactionManager extends DataSourceTransactionManager {
    public DemoTransactionManager(DataSource dataSource) {
        super(dataSource);
    }
}
