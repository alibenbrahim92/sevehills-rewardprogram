package com.rewards.program.service;

import com.rewards.program.dtos.TransactionDTO;

/**
 *
 * @author Ali BEN BRAHIM
 *
 */
public interface TransactionService {
	TransactionDTO addTransaction(TransactionDTO transactionDTO);
	TransactionDTO findById(Long transactionId);
}
