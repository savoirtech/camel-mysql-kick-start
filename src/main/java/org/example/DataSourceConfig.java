package org.example;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jdbc.JdbcComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import com.mysql.cj.jdbc.MysqlDataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/pizza_orders");
        dataSource.setUser("pizza_user");
        dataSource.setPassword("pizza_password");
        return dataSource;
    }

    @Bean
    public JdbcComponent jdbcComponent(DataSource dataSource, CamelContext camelContext) {
        JdbcComponent jdbcComponent = new JdbcComponent();
        jdbcComponent.setDataSource(dataSource);
        camelContext.addComponent("jdbc", jdbcComponent);
        return jdbcComponent;
    }
}
