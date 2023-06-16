package ru.job4j.accidents.configuration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "ru.job4j.accidents.repository")
@PropertySource("/application-test.properties")
@EnableTransactionManagement
public class TestConfig {
    @Bean
    public DataSource ds(@Value("${datasource.driverClassName}") String driver,
                         @Value("${datasource.url}") String url,
                         @Value("${datasource.username}") String username,
                         @Value("${datasource.password}") String password) {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(driver);
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        return ds;
    }
}
