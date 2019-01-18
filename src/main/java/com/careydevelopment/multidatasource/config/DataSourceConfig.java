package com.careydevelopment.multidatasource.config;

import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataSourceConfig {

	@Bean
	public AppRoutingDataSource dataSource() {
		AppRoutingDataSource multiTenantDataSource = new AppRoutingDataSource();
		
		multiTenantDataSource.setTargetDataSources(new ConcurrentHashMap<>());
		multiTenantDataSource.setDefaultTargetDataSource(defaultDataSource());
		multiTenantDataSource.afterPropertiesSet();
		
		return multiTenantDataSource;
	}
	
	
	private DriverManagerDataSource defaultDataSource() {
		DriverManagerDataSource defaultDataSource = new DriverManagerDataSource();
		defaultDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		defaultDataSource.setUrl("jdbc:mysql://myhost.com:3306/fishingsupplies");
		defaultDataSource.setUsername("bubba");
		defaultDataSource.setPassword("password");
		return defaultDataSource;
	}
}
