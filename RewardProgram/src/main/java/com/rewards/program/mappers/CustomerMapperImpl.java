package com.rewards.program.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.rewards.program.dtos.CustomerDTO;
import com.rewards.program.entity.Customer;

/**
 *
 * @author Ali BEN BRAHIM
 *
 */
@Service
public class CustomerMapperImpl implements CustomerMapper{
	
	@Override
	public CustomerDTO toCustomerDTO(Customer customer) {
		CustomerDTO customerDTO = new CustomerDTO();
		BeanUtils.copyProperties(customer, customerDTO);
		return customerDTO;
	}
	
	@Override
	public Customer fromCustomerDTO(CustomerDTO customerDTO) {
		Customer customer = new Customer();
		BeanUtils.copyProperties(customerDTO, customer);
		return customer;
	}
}
