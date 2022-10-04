package com.rewards.program.mappers;

import com.rewards.program.dtos.CustomerDTO;
import com.rewards.program.entity.Customer;

/**
 *
 * @author Ali BEN BRAHIM
 *
 */
public interface CustomerMapper {
	CustomerDTO toCustomerDTO(Customer customer);
	Customer fromCustomerDTO(CustomerDTO customerDTO);
}
