package com.rewards.program.service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rewards.program.dtos.RewardsDTO;
import com.rewards.program.entity.Transaction;
import com.rewards.program.repository.TransactionRepository;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Ali BEN BRAHIM
 *
 */
@Service
@Slf4j
public class RewardsProgramServiceImpl implements RewardsProgramService {

	@Value("${first.reward.limit}")
	private int FIRST_REWARD_LIMIT;
	@Value("${second.reward.limit}")
	private int SECOND_REWARD_LIMIT;
	@Value("${first.limit.coefficient}")
	private int FIRST_LIMIT_COEFFICIENT;
	@Value("${second.limit.coefficient}")
	private int SECOND_LIMIT_COEFFICIENT;
	
	
	@Autowired
	private Clock clock;
	
	@Autowired
	TransactionRepository transactionRepository;

	/**
	 * Calculate customer reward points
	 * @author Ali BEN BRAHIM
	 * @param customerId the customer Id
	 * @return The rewardsDTO Object containing reward points for the last three months period
	 */
	public RewardsDTO getCustomerRewardsById(Long customerId) {
		
		if(log.isDebugEnabled()) {
			log.debug("Calculating customer rewards (Customer Id = {})",customerId);
		}
		
		LocalDateTime now = LocalDate.now(clock).atTime(LocalTime.now(clock));
		LocalDateTime lastMonthPeriod = now.minusMonths(1);
		LocalDateTime lastSecondMonthPeriod = now.minusMonths(2);
		LocalDateTime lastThirdMonthPeriod = now.minusMonths(3);

		List<Transaction> lastMonthTransactions = transactionRepository
				.findByCustomerIdAndDateBetween(customerId, lastMonthPeriod, now);
		List<Transaction> lastSecondMonthTransactions = transactionRepository
				.findByCustomerIdAndDateBetween(customerId, lastSecondMonthPeriod,
						lastMonthPeriod);
		List<Transaction> lastThirdMonthTransactions = transactionRepository
				.findByCustomerIdAndDateBetween(customerId, lastThirdMonthPeriod,
						lastSecondMonthPeriod);

		Long lastMonthRewardPoints = getMonthlyRewards(lastMonthTransactions);
		Long lastSecondMonthRewardPoints = getMonthlyRewards(lastSecondMonthTransactions);
		Long lastThirdMonthRewardPoints = getMonthlyRewards(lastThirdMonthTransactions);

		RewardsDTO rewardsDTO = new RewardsDTO();
		rewardsDTO.setCustomerId(customerId);
		rewardsDTO.setLastMonthPeriodRewardPoints(lastMonthRewardPoints);
		rewardsDTO.setLastSecondMonthPeriodRewardPoints(lastSecondMonthRewardPoints);
		rewardsDTO.setLastThirdMonthPeriodRewardPoints(lastThirdMonthRewardPoints);
		rewardsDTO.setTotalRewardPoints(
				lastMonthRewardPoints + lastSecondMonthRewardPoints + lastThirdMonthRewardPoints);

		return rewardsDTO;

	}

	private Long getMonthlyRewards(List<Transaction> transactions) {
		return transactions.stream().map(transaction -> calculateRewards(transaction))
				.reduce(0L, Long::sum);
	}

	private Long calculateRewards(Transaction transaction) {
		if (transaction.getAmount() > FIRST_REWARD_LIMIT && transaction.getAmount() <= SECOND_REWARD_LIMIT) {
			return Math.round(transaction.getAmount() - FIRST_REWARD_LIMIT) * FIRST_LIMIT_COEFFICIENT;
		} else if (transaction.getAmount() > SECOND_REWARD_LIMIT) {
			return Math.round(transaction.getAmount() - SECOND_REWARD_LIMIT) * SECOND_LIMIT_COEFFICIENT
					+ (SECOND_REWARD_LIMIT - FIRST_REWARD_LIMIT);
		} else {
			return 0l;
		}
	}

}
