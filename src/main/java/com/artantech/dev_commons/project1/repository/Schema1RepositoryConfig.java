package com.artantech.dev_commons.project1.repository;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories(
        basePackages = "com.artantech.dev_commons.project1.repository",
        entityManagerFactoryRef = "schema1EntityManagerFactory",
        transactionManagerRef = "schema1TransactionManager")
@EnableTransactionManagement
public class Schema1RepositoryConfig {
}