package com.rewards.program.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.rewards.program.entity.Transaction;


/**
 *
 * @author Ali BEN BRAHIM
 *
 */
public interface TransactionRepository extends CrudRepository<Transaction,Long> {
    List<Transaction> findByCustomerId(Long customerId);
    List<Transaction> findByCustomerIdAndDateBetween(Long customerId, LocalDateTime fromDate, LocalDateTime toDate);
}
