package com.careydevelopment.multidatasource.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.careydevelopment.multidatasource.config.MultiTenantManager;
import com.careydevelopment.multidatasource.model.Customer;
import com.careydevelopment.multidatasource.repository.CustomerRepository;

@RestController
public class CustomerController {

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	MultiTenantManager multiTenantManager;
	
    @PostMapping("/app/{tenantId}/customer") 
    public Customer createCustomer(@PathVariable String tenantId, @RequestBody Customer customer) {
    	//set the correct database
    	multiTenantManager.setCurrentTenant(tenantId);
    	
    	//persist the customer
    	customer = customerRepository.save(customer);
    	
    	return customer;
    }
}
