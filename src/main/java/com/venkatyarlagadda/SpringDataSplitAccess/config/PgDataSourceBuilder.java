package com.venkatyarlagadda.SpringDataSplitAccess.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.postgresql.jdbc3.Jdbc3PoolingDataSource;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertyNameAliases;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.boot.jdbc.DatabaseDriver;

@SuppressWarnings("deprecation")
public class PgDataSourceBuilder<T extends DataSource> {

	private Map<String, String> properties = new HashMap<>();

	public static PgDataSourceBuilder<?> create() {
		return new PgDataSourceBuilder<>();
	}

	private PgDataSourceBuilder() {
	}

	@SuppressWarnings("unchecked")
	public T build() {
		Class<? extends DataSource> type = (Class<? extends DataSource>) Jdbc3PoolingDataSource.class;
		DataSource result = BeanUtils.instantiateClass(type);
		maybeGetDriverClassName();
		bind(result);
		return (T) result;
	}

	private void maybeGetDriverClassName() {
		if (!this.properties.containsKey("driverClassName") && this.properties.containsKey("url")) {
			String url = this.properties.get("url");
			String driverClass = DatabaseDriver.fromJdbcUrl(url).getDriverClassName();
			this.properties.put("driverClassName", driverClass);
		}
	}

	private void bind(DataSource result) {
		ConfigurationPropertySource source = new MapConfigurationPropertySource(this.properties);
		ConfigurationPropertyNameAliases aliases = new ConfigurationPropertyNameAliases();
		aliases.addAliases("url", "jdbc-url");
		aliases.addAliases("username", "user");
		Binder binder = new Binder(source.withAliases(aliases));
		binder.bind(ConfigurationPropertyName.EMPTY, Bindable.ofInstance(result));
	}

	public PgDataSourceBuilder<T> url(String url) {
		this.properties.put("url", url);
		return this;
	}

	public PgDataSourceBuilder<T> driverClassName(String driverClassName) {
		this.properties.put("driverClassName", driverClassName);
		return this;
	}

	public PgDataSourceBuilder<T> username(String username) {
		this.properties.put("username", username);
		return this;
	}

	public PgDataSourceBuilder<T> password(String password) {
		this.properties.put("password", password);
		return this;
	}
}