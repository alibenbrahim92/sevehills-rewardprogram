package com.rewards.program.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rewards.program.dtos.TransactionDTO;
import com.rewards.program.entity.Transaction;

/**
 *
 * @author Ali BEN BRAHIM
 *
 */
@Service
public class TransactionMapperImpl implements TransactionMapper {
	
	@Autowired
	CustomerMapper customerMapper;
	
	@Override
	public TransactionDTO toTransactionDTO(Transaction transaction) {
		TransactionDTO transactionDTO = new TransactionDTO();
		BeanUtils.copyProperties(transaction, transactionDTO);
		transactionDTO.setCustomerDTO(customerMapper.toCustomerDTO(transaction.getCustomer()));
		return transactionDTO;
	}

	@Override
	public Transaction fromTransactionDTO(TransactionDTO transactionDTO) {
		Transaction transaction = new Transaction();
		BeanUtils.copyProperties(transactionDTO, transaction);
		transaction.setCustomer(customerMapper.fromCustomerDTO(transactionDTO.getCustomerDTO()));
		return transaction;
	}
}
