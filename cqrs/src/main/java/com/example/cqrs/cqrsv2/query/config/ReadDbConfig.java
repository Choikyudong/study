package com.example.cqrs.cqrsv2.query.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
		basePackages = "com.example.cqrs.cqrsv2.query.repository",
		entityManagerFactoryRef = "readEntityManagerFactory",
		transactionManagerRef = "readTransactionManager"
)
public class ReadDbConfig {

	@Bean
	@ConfigurationProperties("spring.datasource.read")
	public DataSourceProperties readDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	public DataSource readDataSource(@Qualifier("readDataSourceProperties") DataSourceProperties properties) {
		return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}

	@Bean
	public PlatformTransactionManager readTransactionManager(@Qualifier("readEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean readEntityManagerFactory(EntityManagerFactoryBuilder builder,
																		   @Qualifier("readDataSource") DataSource dataSource) {
		return builder
				.dataSource(dataSource)
				.packages("com.example.cqrs.cqrsv2.query.model.entity")
				.persistenceUnit("read")
				.build();
	}

}
