package com.venkatyarlagadda.SpringDataSplitAccess.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import com.venkatyarlagadda.SpringDataSplitAccess.enums.Route;
import com.venkatyarlagadda.SpringDataSplitAccess.routing.RoutingDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * 
 * @author Venkat Yarlagadda
 * @version V1
 *
 */
@Configuration
public class DataSourceConfig {
	
	private static final String MASTER_DATASOURCE_PREFIX = "spring.master-db.datasource";
	private static final String REPLICA_DATASOURCE_PREFIX = "spring.replica-db.datasource";

	@Autowired
	private Environment environment;

	@Bean
	@Primary
	public DataSource dataSource() {
		final RoutingDataSource routingDataSource = new RoutingDataSource();

		final DataSource masterDataSource = buildDataSource("PrimaryHikariPool", MASTER_DATASOURCE_PREFIX);
		final DataSource replicaDataSource = buildDataSource("ReplicaHikariPool", REPLICA_DATASOURCE_PREFIX);

		final Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put(Route.MASTER, masterDataSource);
		targetDataSources.put(Route.REPLICA, replicaDataSource);

		routingDataSource.setTargetDataSources(targetDataSources);
		routingDataSource.setDefaultTargetDataSource(masterDataSource);

		return routingDataSource;
	}

	private DataSource buildDataSource(String poolName, String dataSourcePrefix) {
		final HikariConfig hikariConfig = new HikariConfig();

		hikariConfig.setPoolName(poolName);
		hikariConfig.setJdbcUrl(environment.getProperty(String.format("%s.url", dataSourcePrefix)));
		hikariConfig.setUsername(environment.getProperty(String.format("%s.username", dataSourcePrefix)));
		hikariConfig.setPassword(environment.getProperty(String.format("%s.password", dataSourcePrefix)));
		hikariConfig.setDriverClassName(environment.getProperty(String.format("%s.driver-class-name", dataSourcePrefix)));
		
		return new HikariDataSource(hikariConfig);
	}
}