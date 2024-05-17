package com.begh.customerservice.repository;

import com.begh.customerservice.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    Customer findCustomerByEmail(String email);
}
