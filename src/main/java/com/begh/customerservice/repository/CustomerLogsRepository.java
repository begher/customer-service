package com.begh.customerservice.repository;

import com.begh.customerservice.model.CustomerLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerLogsRepository extends JpaRepository<CustomerLogs, Long> {
}
