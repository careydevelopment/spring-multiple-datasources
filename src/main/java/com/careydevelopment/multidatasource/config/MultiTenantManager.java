package com.careydevelopment.multidatasource.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class MultiTenantManager implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger logger = LoggerFactory.getLogger(MultiTenantManager.class);
	
	private final Map<Object, Object> tenantDataSources = new ConcurrentHashMap<>();
	private final DataSourceProperties properties;
	
	@Autowired
	private AppRoutingDataSource routingDataSource;
	
	public MultiTenantManager(DataSourceProperties properties) {
		this.properties = properties;
	}
	
	public void setCurrentTenant(String tenantId)  {
		if (tenantIsAbsent(tenantId)) {
			throw new RuntimeException("No tenant with ID " + tenantId);
		}
		
		routingDataSource.setCurrentTenant(tenantId);
		logger.debug("Tenant '{}' set as current.", tenantId);
	}

	
	public void addTenant(String tenantId, String url, String username, String password) throws SQLException {
		DataSource dataSource = DataSourceBuilder.create()
				.driverClassName(properties.getDriverClassName())
				.url(url)
				.username(username)
				.password(password)
				.build();

		try(Connection c = dataSource.getConnection()) {
			tenantDataSources.put(tenantId, dataSource);
			routingDataSource.setTargetDataSources(tenantDataSources);
			routingDataSource.afterPropertiesSet();
			logger.debug("Tenant '{}' added.", tenantId);
		}
	}

	public DataSource removeTenant(String tenantId) {
		Object removedDataSource = tenantDataSources.remove(tenantId);
		routingDataSource.setTargetDataSources(tenantDataSources);
		routingDataSource.afterPropertiesSet();
		return (DataSource) removedDataSource;
	}

	public boolean tenantIsAbsent(String tenantId) {
		return !tenantDataSources.containsKey(tenantId);
	}

	public Collection<Object> getTenantList() {
		return tenantDataSources.keySet();
	}	
	
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
        	addTenant("fishingsupplies", "jdbc:mysql://myhost.com:3306/fishingsupplies", "bubba", "password");
        	addTenant("gadgetwarehouse", "jdbc:mysql://myhost.com:3306/gadgetwarehouse", "bubba", "password");
        } catch (Exception e ) {
        	e.printStackTrace();
        }
    }
}
