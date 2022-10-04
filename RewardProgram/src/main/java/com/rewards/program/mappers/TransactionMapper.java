package com.rewards.program.mappers;

import com.rewards.program.dtos.TransactionDTO;
import com.rewards.program.entity.Transaction;

/**
 *
 * @author Ali BEN BRAHIM
 *
 */
public interface TransactionMapper {
	TransactionDTO toTransactionDTO(Transaction transaction);
	Transaction fromTransactionDTO(TransactionDTO transactionDTO);
}
