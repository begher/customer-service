package com.begh.customerservice.service;

import com.begh.customerservice.model.Customer;
import com.begh.customerservice.repository.CustomerRepository;
import com.begh.customerservice.util.IdProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repo;
    private final UserActivityLoggingService log;

    public boolean addCustomerIfNotExist(String email){
        Customer customer = repo.findCustomerByEmail(email);
        if(customer == null){
            customer = repo.save(
                    Customer.builder()
                            .id(IdProvider.getRandomId())
                            .email(email)
                            .createdAt(LocalDateTime.now())
                            .lastActivity(LocalDateTime.now())
                            .build()
            );
            log.logActivity(customer.getId() + " " + customer.getEmail(), "New user");
            return true;
        }
        customer.setLastActivity(LocalDateTime.now());
        repo.save(customer);
        log.logActivity(customer.getId() + " " + customer.getEmail(), "login");
        return false;
    }
}

