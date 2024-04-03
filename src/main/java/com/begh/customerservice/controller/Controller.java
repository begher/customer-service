package com.begh.customerservice.controller;

import com.begh.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {

    //private final CustomerService service;

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("Emil is da king");
    }



}
