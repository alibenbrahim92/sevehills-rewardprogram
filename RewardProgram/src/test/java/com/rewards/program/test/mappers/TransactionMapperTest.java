package com.rewards.program.test.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.rewards.program.dtos.CustomerDTO;
import com.rewards.program.dtos.TransactionDTO;
import com.rewards.program.entity.Customer;
import com.rewards.program.entity.Transaction;
import com.rewards.program.mappers.CustomerMapper;
import com.rewards.program.mappers.TransactionMapper;
import com.rewards.program.mappers.TransactionMapperImpl;

/**
 *
 * @author Ali BEN BRAHIM
 *
 */
@SpringBootTest(classes = {TransactionMapperImpl.class})
public class TransactionMapperTest {
	
	public static final Long ID = 1L;
	private TransactionDTO transactionDTO;
	private CustomerDTO customerDTO;
	private Customer customer;
	private Transaction transaction;
	
	
	@Autowired
	private TransactionMapper transactionMapper;
	
	@MockBean
	private CustomerMapper customerMapper;
	
	@BeforeEach
	public void beforeEach() {
		Mockito.when(customerMapper.fromCustomerDTO(Mockito.any())).thenReturn(customer);
		Mockito.when(customerMapper.toCustomerDTO(Mockito.any())).thenReturn(customerDTO);
	}
	
	public TransactionMapperTest() {
		transactionDTO = new TransactionDTO();
		transactionDTO.setId(ID);
		transactionDTO.setDate(LocalDateTime.of(2022,9,1,0,0,0));
		transactionDTO.setAmount(300);
		customerDTO = new CustomerDTO(ID,"Tom");
		customer = new Customer();
		customer.setId(ID);
		customer.setName("Tom");
		transactionDTO.setCustomerDTO(customerDTO);
		transaction = new Transaction(ID,customer,LocalDateTime.of(2022,9,1,0,0,0), 300L);
	}
	
	
	@Test
	void fromTransactionDTOTest() {
		Transaction transactionResult = transactionMapper.fromTransactionDTO(transactionDTO);
		assertEquals(transaction,transactionResult);
	}
	
	@Test
	void toTransactionDTOTest() {
		TransactionDTO transactionDTOResult = transactionMapper.toTransactionDTO(transaction);
		assertEquals(transactionDTO,transactionDTOResult);
	}
}
