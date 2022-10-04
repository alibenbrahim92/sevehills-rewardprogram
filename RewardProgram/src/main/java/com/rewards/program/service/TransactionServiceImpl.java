package com.rewards.program.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rewards.program.dtos.TransactionDTO;
import com.rewards.program.entity.Customer;
import com.rewards.program.entity.Transaction;
import com.rewards.program.mappers.TransactionMapper;
import com.rewards.program.repository.CustomerRepository;
import com.rewards.program.repository.TransactionRepository;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Ali BEN BRAHIM
 *
 */
@Service
@Transactional
@Slf4j
public class TransactionServiceImpl implements TransactionService{
	
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private TransactionMapper transactionMapper;
	@Autowired
	private CustomerRepository customerRepository;
	

	/**
	 * Add a transaction to the databse
	 * @author Ali BEN BRAHIM
	 * @param transactionDTO the transaction data to save, transactionId must be nulll
	 * @return TransactionDTO Object retrieved from the database
	 */
	@Override
	public TransactionDTO addTransaction(TransactionDTO transactionDTO) {
		
		if(log.isDebugEnabled()) {
			log.debug("Adding new transaction");
		}
		
		if(transactionDTO.getId() != null) {
			throw new RuntimeException("Transaction Id must be null in creation");
		}
		if(transactionDTO.getCustomerDTO() == null || transactionDTO.getCustomerDTO().getId() == null) {
			throw new RuntimeException("Customer must not be null");
		}
		
		Customer customer = customerRepository.findById(transactionDTO.getCustomerDTO().getId())
				.orElseThrow(()-> {
					log.error("Customer ID = {} not found",transactionDTO.getCustomerDTO().getId());
					return new RuntimeException("Customer ID = "+transactionDTO.getCustomerDTO().getId()+" not found");
				});

		Transaction transaction = transactionMapper.fromTransactionDTO(transactionDTO);
		transaction.setCustomer(customer);
		transaction = transactionRepository.save(transaction);
		return transactionMapper.toTransactionDTO(transaction);
	}
	
	/**
	 * Find a transaction by its id
	 * @author Ali BEN BRAHIM
	 * @param transactionId the transaction id
	 * @return TransactionDTO Object containing transaction data
	 */
	@Override
	public TransactionDTO findById(Long transactionId) {
		
		if(log.isDebugEnabled()) {
			log.debug("Searching transaction with Id = {}",transactionId);
		}
		
		Transaction transaction = transactionRepository.findById(transactionId)
				.orElseThrow(()-> {
					log.error("Transaction ID = {} not found",transactionId);
					return new RuntimeException("Transaction ID = "+transactionId+" not found");
				});
		return transactionMapper
				.toTransactionDTO(transaction);
	}
}
