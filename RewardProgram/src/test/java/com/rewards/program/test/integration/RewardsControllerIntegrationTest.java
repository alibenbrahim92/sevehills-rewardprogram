package com.rewards.program.test.integration;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rewards.program.dtos.CustomerDTO;
import com.rewards.program.dtos.RewardsDTO;
import com.rewards.program.entity.Customer;
import com.rewards.program.entity.Transaction;
import com.rewards.program.repository.CustomerRepository;
import com.rewards.program.repository.TransactionRepository;

/**
 * 
 * @author Ali BEN BRAHIM
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
public class RewardsControllerIntegrationTest {

	private RewardsDTO rewardsDTO;
	private Customer customer;
	private Transaction transaction1;
	private Transaction transaction2;
	private Transaction transaction3;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private ObjectMapper mapper;

	
	public RewardsControllerIntegrationTest() {
		
		rewardsDTO = new RewardsDTO();
		rewardsDTO.setCustomerId(1L);
		rewardsDTO.setLastMonthPeriodRewardPoints(90L);
		rewardsDTO.setLastSecondMonthPeriodRewardPoints(10L);
		rewardsDTO.setLastThirdMonthPeriodRewardPoints(30L);
		rewardsDTO.setTotalRewardPoints(130L);
		
		customer = new Customer();
		customer.setId(1L);
		customer.setName("Tom");
		
		transaction1 = new Transaction(1L,customer,LocalDateTime.now().minusDays(1),120d);
		transaction2 = new Transaction(2L,customer,LocalDateTime.now().minusDays(32),60d);
		transaction3 = new Transaction(3L,customer,LocalDateTime.now().minusDays(64),80d);
	}
	
	@Test
	public void getCustomerRewardsByIdTest() throws Exception {
		
		customerRepository.save(customer);
		transactionRepository.save(transaction1);
		transactionRepository.save(transaction2);
		transactionRepository.save(transaction3);
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/customer/{customerId}/rewardPoints",1))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.customerId").value(1))
			.andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(rewardsDTO)));
	}
	
	@Test
	void addCustomer() throws Exception {

		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setName("Tom");
		CustomerDTO customerDTOExpected = new CustomerDTO();
		customerDTOExpected.setId(1L);
		customerDTOExpected.setName("Tom");
		
		this.mockMvc.perform(MockMvcRequestBuilders.post("/customer")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapper.writeValueAsString(customerDTO)))
			.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(customerDTOExpected)));
	}
}
