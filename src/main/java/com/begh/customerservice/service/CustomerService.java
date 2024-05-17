package com.begh.customerservice.service;

import com.begh.customerservice.model.Customer;
import com.begh.customerservice.model.CustomerLogs;
import com.begh.customerservice.repository.CustomerLogsRepository;
import com.begh.customerservice.repository.CustomerRepository;
import com.begh.customerservice.util.IdProvider;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repo;
    private final CustomerLogsRepository logRepo;

    public boolean addCustomerIfNotExist(String email, HttpServletRequest request){
        Customer customer = repo.findCustomerByEmail(email);
        LocalDateTime now = LocalDateTime.now();
        if(customer == null){
            customer = repo.save(
                    Customer.builder()
                            .id(IdProvider.getRandomId())
                            .email(email)
                            .createdAt(now)
                            .lastActivity(now)
                            .build()
            );
            logRepo.save(CustomerLogs.builder()
                            .customer(customer)
                            .activity(now)
                            .message("New user")
                            .ipAddress(getClientIp(request))
                            .userAgent(request.getHeader("User-Agent"))
                    .build());
            return true;
        }
        customer.setLastActivity(LocalDateTime.now());
        repo.save(customer);
        logRepo.save(CustomerLogs.builder()
                .customer(customer)
                .activity(now)
                .message("Login")
                .ipAddress(getClientIp(request))
                .userAgent(request.getHeader("User-Agent"))
                .build());
        return false;
    }

    private String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }
}

