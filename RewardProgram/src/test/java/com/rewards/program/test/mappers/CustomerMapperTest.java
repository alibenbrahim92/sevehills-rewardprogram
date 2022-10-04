package com.rewards.program.test.mappers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rewards.program.dtos.CustomerDTO;
import com.rewards.program.entity.Customer;
import com.rewards.program.mappers.CustomerMapper;
import com.rewards.program.mappers.CustomerMapperImpl;

/**
 *
 * @author Ali BEN BRAHIM
 *
 */
@SpringBootTest(classes = {CustomerMapperImpl.class})
public class CustomerMapperTest {
	
	public static final Long ID = 1L;
	
	private Customer customer;
	private CustomerDTO customerDTO;
	
	@Autowired
	private CustomerMapper customerMapper;
	
	public CustomerMapperTest() {
		customer = new Customer();
		customer.setId(ID);
		customer.setName("Tom");
		customerDTO = new CustomerDTO();
		customerDTO.setId(ID);
		customerDTO.setName("Tom");
	}
	
	@Test
	public void fromCustomerDTOTest() {
		Customer customerResult = customerMapper.fromCustomerDTO(customerDTO);
		Assertions.assertEquals(customer, customerResult);
	}
	
	@Test
	public void toCustomerDTOTest() {
		CustomerDTO customerDTOResult = customerMapper.toCustomerDTO(customer);
		Assertions.assertEquals(customerDTO, customerDTOResult);
	}
}
