package com.interall.dev_commons.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;
@Configuration
public class DataSourceConfig {

    @Bean(name = "schema1DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.schema1")
    public DataSource schema1DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "schema2DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.schema2")
    public DataSource schema2DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "schema1EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean schema1EntityManagerFactory(
            @Qualifier("schema1DataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.example.devcommons.model.schema1");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setPersistenceUnitName("schema1");

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.OracleDialect");
        properties.put("hibernate.hbm2ddl.auto", "none");
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean(name = "schema2EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean schema2EntityManagerFactory(
            @Qualifier("schema2DataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.example.devcommons.model.schema2");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setPersistenceUnitName("schema2");

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.OracleDialect");
        properties.put("hibernate.hbm2ddl.auto", "none");
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean(name = "schema1TransactionManager")
    public JpaTransactionManager schema1TransactionManager(
            @Qualifier("schema1EntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory.getObject());
    }

    @Bean(name = "schema2TransactionManager")
    public JpaTransactionManager schema2TransactionManager(
            @Qualifier("schema2EntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory.getObject());
    }
}
