package com.rewards.program.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rewards.program.dtos.CustomerDTO;
import com.rewards.program.entity.Customer;
import com.rewards.program.mappers.CustomerMapper;
import com.rewards.program.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;


/**
 *
 * @author Ali BEN BRAHIM
 *
 */
@Service
@Transactional
@Slf4j
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private CustomerMapper customerMapper;
	

	/**
	 * Add a customer to the database
	 * @author Ali BEN BRAHIM
	 * @param customerDTO Object containing customer data, customerId must be null
	 * @return CustomerDTO Object retrieved from the database
	 */
	@Override
	public CustomerDTO addCustomer(CustomerDTO customerDTO) {
		
		if(log.isDebugEnabled()) {
			log.debug("Adding new customer");
		}
		
		if(customerDTO.getId() != null) {
			log.error("Customer Id must be null ");
			throw new RuntimeException("Customer Id must be null in creation");
		}
		
		Customer customer = customerMapper.fromCustomerDTO(customerDTO);
		return customerMapper.toCustomerDTO(customerRepository.save(customer));
	}
	
	/**
	 * Find a customer by its id
	 * @author Ali BEN BRAHIM
	 * @param customerId the customer id
	 * @return CustomerDTO Object containing customer data
	 */
	@Override
	public CustomerDTO findCustomerByID(Long customerId) {
		
		if(log.isDebugEnabled()) {
			log.debug("Searching customer with ID = {}",customerId);
		}

		Customer customer = customerRepository.findById(customerId)
		.orElseThrow(()-> {
			log.error("Customer ID = {} not found",customerId);
			return new RuntimeException("Customer ID = "+customerId+" not found");
		});
		return customerMapper.toCustomerDTO(customer);
	}

}
