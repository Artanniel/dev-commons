package com.artantech.dev_commons.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfig {

    @Bean(name = "flywaySchema1")
    public Flyway flywaySchema1(@Qualifier("schema1DataSource") DataSource dataSource) {
        System.out.println("FlywayConfig#flywaySchema1" + dataSource);
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration_test/schema1")
                .schemas("SCHEMA_1")
                .defaultSchema("SCHEMA_1")
                .createSchemas(true)
                .baselineOnMigrate(true)
                .baselineVersion("0")
                .table("flyway_schema_history")
                .load();
        flyway.migrate();
        return flyway;
    }

    @Bean(name = "flywaySchema2")
    public Flyway flywaySchema2(@Qualifier("schema2DataSource") DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration_test/schema2")
                .schemas("SCHEMA_2")
                .defaultSchema("SCHEMA_2")
                .createSchemas(true)
                .baselineOnMigrate(true)
                .baselineVersion("0")
                .table("flyway_schema_history")
                .load();
        flyway.migrate();
        return flyway;
    }
}