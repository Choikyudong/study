package com.example.cqrs.cqrsv3.command.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
		basePackages = "com.example.cqrs.cqrsv2.command.repository",
		entityManagerFactoryRef = "writeEntityManagerFactory",
		transactionManagerRef = "writeTransactionManager"
)
public class WriteDbConfig {

	@Primary
	@Bean
	@ConfigurationProperties("spring.datasource.write")
	public DataSourceProperties writeDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Primary
	@Bean
	public DataSource writeDataSource(@Qualifier("writeDataSourceProperties") DataSourceProperties properties) {
		return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}

	@Primary
	@Bean
	public PlatformTransactionManager writeTransactionManager(@Qualifier("writeEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

	@Primary
	@Bean
	public LocalContainerEntityManagerFactoryBean writeEntityManagerFactory(EntityManagerFactoryBuilder builder,
																			@Qualifier("writeDataSource") DataSource dataSource) {
		return builder
				.dataSource(dataSource)
				.packages("com.example.cqrs.cqrsv2.command.model")
				.persistenceUnit("write")
				.build();
	}


}
