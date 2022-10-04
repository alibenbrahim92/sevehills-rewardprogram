package com.rewards.program.service;

import com.rewards.program.dtos.CustomerDTO;

/**
 *
 * @author Ali BEN BRAHIM
 *
 */
public interface CustomerService {
	CustomerDTO addCustomer(CustomerDTO customerDTO);
	CustomerDTO findCustomerByID(Long customerId);
}
