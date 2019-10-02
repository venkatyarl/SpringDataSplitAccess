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

/**
 * 
 * @author Venkat Yarlagadda
 * @version V1
 *
 */
@Configuration
public class DataSourceConfig {
	
	private static final String MASTER_DATASOURCE_PREFIX = "spring.datasource.master";
	private static final String REPLICA_DATASOURCE_PREFIX = "spring.datasource.replica";

	@Autowired
	private Environment environment;

	@Bean
	@Primary
	public DataSource dataSource() {
		final RoutingDataSource routingDataSource = new RoutingDataSource();
		final DataSource masterDataSource = createDataSource(MASTER_DATASOURCE_PREFIX);
		final DataSource replicaDataSource = createDataSource(REPLICA_DATASOURCE_PREFIX);

		final Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put(Route.MASTER, masterDataSource);
		targetDataSources.put(Route.REPLICA, replicaDataSource);

		routingDataSource.setTargetDataSources(targetDataSources);
		routingDataSource.setDefaultTargetDataSource(masterDataSource);

		return routingDataSource;
	}
	
	public DataSource createDataSource(String prefix) {
		return PgDataSourceBuilder.create()
				.url(environment.getProperty(String.format("%s.url", prefix)))
				.username(environment.getProperty(String.format("%s.username", prefix)))
				.password(environment.getProperty(String.format("%s.password", prefix)))
				.driverClassName(
						environment.getProperty(String.format("%s.driver-class-name", prefix)))
				.build();
	}
}