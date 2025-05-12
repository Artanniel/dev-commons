package com.interall.dev_commons.project2.repository;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories(
        basePackages = "com.interall.dev_commons.project2.repository",
        entityManagerFactoryRef = "schema2EntityManagerFactory",
        transactionManagerRef = "schema2TransactionManager")
@EnableTransactionManagement
public class Schema2RepositoryConfig {
}