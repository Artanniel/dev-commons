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
        entityManagerFactoryRef = "schema1EntityManagerFactory",
        transactionManagerRef = "schema1TransactionManager",
        basePackages = "com.artantech.dev_commons.project1.repository"
)
public class Schema1JpaConfig {

    @Bean(name = "schema1DataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.schema1")
    public DataSourceProperties schema1DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "schema1DataSource")
    public DataSource schema1DataSource(@Qualifier("schema1DataSourceProperties") DataSourceProperties properties) {
        // Use DataSourceProperties para construir o DataSource
        return properties.initializeDataSourceBuilder().build();
    }

    // Não injetamos EntityManagerFactoryBuilder aqui
    @Bean(name = "schema1EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean schema1EntityManagerFactory(
            // Remova o parâmetro EntityManagerFactoryBuilder builder
            @Qualifier("schema1DataSource") DataSource dataSource
    ) {
        // Crie o EntityManagerFactoryBuilder explicitamente aqui se precisar de um builder personalizado
        // Mas para configuração padrão, você pode usar uma abordagem como esta:
        EntityManagerFactoryBuilder builder = new EntityManagerFactoryBuilder(
                new org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter(), // Use HibernateJpaVendorAdapter
                // Você pode adicionar propriedades JPA aqui se necessário
                new java.util.HashMap<>(),
                null
        );

        return builder
                .dataSource(dataSource)
                .packages("com.artantech.dev_commons.project1.entity")
                .persistenceUnit("schema1")
                .build();
    }

    @Bean(name = "schema1TransactionManager")
    public PlatformTransactionManager schema1TransactionManager(
            @Qualifier("schema1EntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory
    ) {
        // Certifique-se de que o objeto EntityManagerFactory não é nulo antes de retornar
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactory.getObject()));
    }
}