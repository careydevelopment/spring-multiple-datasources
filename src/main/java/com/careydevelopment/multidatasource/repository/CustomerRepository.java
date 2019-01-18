package com.careydevelopment.multidatasource.repository;

import org.springframework.data.repository.CrudRepository;
import com.careydevelopment.multidatasource.model.Customer;

public interface CustomerRepository  extends CrudRepository<Customer,Long> {


}
