package com.begh.customerservice.service;

import com.begh.customerservice.model.Customer;
import com.begh.customerservice.repository.CustomerRepository;
import com.begh.customerservice.util.IdProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repo;

    public boolean addCustomerIfNotExist(String email){
        System.out.println(email);
        if(email == "email@email.com"){
            System.out.println("exist");
            return false;
        }
            System.out.println("added with id " + IdProvider.getRandomId());
            return true;
    }
}

