package com.careydevelopment.multidatasource.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class AppRoutingDataSource extends AbstractRoutingDataSource {
	private final ThreadLocal<String> currentTenant = new ThreadLocal<>();
	
	@Override
	protected Object determineCurrentLookupKey() {
		return currentTenant.get();
	}

	public void setCurrentTenant(String tenantId) {
		currentTenant.set(tenantId);
	}

}
