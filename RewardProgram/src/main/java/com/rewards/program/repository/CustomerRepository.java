package com.rewards.program.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.rewards.program.entity.Customer;

/**
 *
 * @author Ali BEN BRAHIM
 *
 */
public interface CustomerRepository extends CrudRepository<Customer,Long> {
    public Optional<Customer> findById(Long customerId);
}
