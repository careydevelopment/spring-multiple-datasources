package com.careydevelopment.multidatasource.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.careydevelopment.multidatasource.config.MultiTenantManager;
import com.careydevelopment.multidatasource.model.Customer;
import com.careydevelopment.multidatasource.model.FullRequest;
import com.careydevelopment.multidatasource.repository.CustomerRepository;

@RestController
public class CustomerController {

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	MultiTenantManager multiTenantManager;
	
    @PostMapping("/customer") 
    public Customer createCustomer(@RequestBody FullRequest fullRequest) {
    	//set the correct database
    	multiTenantManager.setCurrentTenant(fullRequest.getTenant());
    	
    	//persist the customer
    	Customer customer = customerRepository.save(fullRequest.getCustomer());
    	
    	return customer;
    }
}
