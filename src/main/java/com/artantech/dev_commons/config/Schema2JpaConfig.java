package com.artantech.dev_commons.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties; // Importe esta classe
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects; // Importe Objects para usar Objects.requireNonNull

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "schema2EntityManagerFactory",
        transactionManagerRef = "schema2TransactionManager",
        basePackages = "com.artantech.dev_commons.project2.repository"
)
public class Schema2JpaConfig {

    @Bean(name = "schema2DataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.schema2")
    public DataSourceProperties schema2DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "schema2DataSource")
    public DataSource schema2DataSource(@Qualifier("schema2DataSourceProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean(name = "schema2EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean schema2EntityManagerFactory(
            @Qualifier("schema2DataSource") DataSource dataSource
    ) {
        EntityManagerFactoryBuilder builder = new EntityManagerFactoryBuilder(
                new org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter(),
                new java.util.HashMap<>(),
                null
        );

        return builder
                .dataSource(dataSource)
                .packages("com.artantech.dev_commons.project2.entity")
                .persistenceUnit("schema2")
                .build();
    }

    @Bean(name = "schema2TransactionManager")
    public PlatformTransactionManager schema2TransactionManager(
            @Qualifier("schema2EntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory
    ) {
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactory.getObject()));
    }
}