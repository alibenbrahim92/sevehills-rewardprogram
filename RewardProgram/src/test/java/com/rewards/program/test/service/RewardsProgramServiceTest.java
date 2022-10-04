package com.rewards.program.test.service;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.rewards.program.dtos.RewardsDTO;
import com.rewards.program.entity.Customer;
import com.rewards.program.entity.Transaction;
import com.rewards.program.repository.TransactionRepository;
import com.rewards.program.service.RewardsProgramService;
import com.rewards.program.service.RewardsProgramServiceImpl;

@SpringBootTest(classes = RewardsProgramServiceImpl.class)
public class RewardsProgramServiceTest {
	
	private static final Long ID = 1L;
	
	private Customer customer;
	
	LocalDateTime lastMonthPeriod;
	LocalDateTime lastSecondMonthPeriod;
	LocalDateTime lastThirdMonthPeriod;
	LocalDate now;
	LocalTime currentTime;
	
	@Autowired
	RewardsProgramService rewardsProgramService;
	
	@MockBean
	TransactionRepository transactionReporisory;
	
	@MockBean
	Clock clock;
	
	public RewardsProgramServiceTest() {
		customer = new Customer();
		customer.setId(ID);
		customer.setName("Tom");
	}
	
	@BeforeEach
	private void before() {
		
		Mockito.when(clock.instant()).thenReturn(Instant.now());
		Mockito.when(clock.getZone()).thenReturn(Clock.systemDefaultZone().getZone());
		now = LocalDate.now(clock);
		currentTime = LocalTime.now(clock);
		lastMonthPeriod = now.minusMonths(1).atTime(currentTime);
		lastSecondMonthPeriod = now.minusMonths(2).atTime(currentTime);
		lastThirdMonthPeriod = now.minusMonths(3).atTime(currentTime);
	}
	
	@Test
	public void getCustomerRewards_whenAmountBelowFirstLimit() {
		
		List<Transaction> transactions1 = Arrays.asList(new Transaction(1L,customer,now.atTime(currentTime),40d));
		
		Mockito.when(transactionReporisory.findByCustomerIdAndDateBetween(ID, lastMonthPeriod, now.atTime(currentTime)))
			.thenReturn(transactions1);
		Mockito.when(transactionReporisory.findByCustomerIdAndDateBetween(ID, lastSecondMonthPeriod, lastMonthPeriod))
			.thenReturn(new ArrayList<>());
		Mockito.when(transactionReporisory.findByCustomerIdAndDateBetween(ID, lastThirdMonthPeriod, lastSecondMonthPeriod))
			.thenReturn(new ArrayList<>());
		
		RewardsDTO rewardsDTOExpected = new RewardsDTO();
		rewardsDTOExpected.setCustomerId(customer.getId());
		rewardsDTOExpected.setLastMonthPeriodRewardPoints(0L);
		rewardsDTOExpected.setLastSecondMonthPeriodRewardPoints(0L);
		rewardsDTOExpected.setLastThirdMonthPeriodRewardPoints(0L);
		rewardsDTOExpected.setTotalRewardPoints(0L);
		
		Assertions.assertEquals(rewardsDTOExpected, rewardsProgramService.getCustomerRewardsById(ID));
	}
	
	@Test
	public void getCustomerRewards_whenAmountSuperiorToFirstLimitAndInferiorToSecondLimit() {
		
		List<Transaction> transactions1 = Arrays.asList(new Transaction(1L,customer,now.atTime(currentTime),70d));
		
		Mockito.when(transactionReporisory.findByCustomerIdAndDateBetween(ID, lastMonthPeriod, now.atTime(currentTime)))
			.thenReturn(transactions1);
		Mockito.when(transactionReporisory.findByCustomerIdAndDateBetween(ID, lastSecondMonthPeriod, lastMonthPeriod))
			.thenReturn(new ArrayList<>());
		Mockito.when(transactionReporisory.findByCustomerIdAndDateBetween(ID, lastThirdMonthPeriod, lastSecondMonthPeriod))
			.thenReturn(new ArrayList<>());
		
		
		RewardsDTO rewardsDTOExpected = new RewardsDTO();
		rewardsDTOExpected.setCustomerId(customer.getId());
		rewardsDTOExpected.setLastMonthPeriodRewardPoints(20L);
		rewardsDTOExpected.setLastSecondMonthPeriodRewardPoints(0L);
		rewardsDTOExpected.setLastThirdMonthPeriodRewardPoints(0L);
		rewardsDTOExpected.setTotalRewardPoints(20L);
		
		Assertions.assertEquals(rewardsDTOExpected, rewardsProgramService.getCustomerRewardsById(ID));
	}
	
	@Test
	public void getCustomerRewards_whenAmountSuperiorToSecondLimit() {
		
		List<Transaction> transactions1 = Arrays.asList(new Transaction(1L,customer,now.atTime(currentTime),120d));
		
		Mockito.when(transactionReporisory.findByCustomerIdAndDateBetween(ID, lastMonthPeriod, now.atTime(currentTime)))
			.thenReturn(transactions1);
		Mockito.when(transactionReporisory.findByCustomerIdAndDateBetween(ID, lastSecondMonthPeriod, lastMonthPeriod))
			.thenReturn(new ArrayList<>());
		Mockito.when(transactionReporisory.findByCustomerIdAndDateBetween(ID, lastThirdMonthPeriod, lastSecondMonthPeriod))
			.thenReturn(new ArrayList<>());
		
		RewardsDTO rewardsDTOExpected = new RewardsDTO();
		rewardsDTOExpected.setCustomerId(customer.getId());
		rewardsDTOExpected.setLastMonthPeriodRewardPoints(90L);
		rewardsDTOExpected.setLastSecondMonthPeriodRewardPoints(0L);
		rewardsDTOExpected.setLastThirdMonthPeriodRewardPoints(0L);
		rewardsDTOExpected.setTotalRewardPoints(90L);
		
		Assertions.assertEquals(rewardsDTOExpected, rewardsProgramService.getCustomerRewardsById(ID));
	}
	
	
	@Test
	public void getCustomerRewards_whenMultipleTransactionsSamePeriod() {
		
		List<Transaction> transactions1 = Arrays.asList(new Transaction(1L,customer,now.atTime(currentTime),70d),
				new Transaction(2L,customer,now.atTime(currentTime),40d),
				new Transaction(3L,customer,now.atTime(currentTime),120d));
		
		Mockito.when(transactionReporisory.findByCustomerIdAndDateBetween(ID, lastMonthPeriod, now.atTime(currentTime)))
			.thenReturn(transactions1);
		Mockito.when(transactionReporisory.findByCustomerIdAndDateBetween(ID, lastSecondMonthPeriod, lastMonthPeriod))
			.thenReturn(new ArrayList<>());
		Mockito.when(transactionReporisory.findByCustomerIdAndDateBetween(ID, lastThirdMonthPeriod, lastSecondMonthPeriod))
			.thenReturn(new ArrayList<>());
		
		
		RewardsDTO rewardsDTOExpected = new RewardsDTO();
		rewardsDTOExpected.setCustomerId(customer.getId());
		rewardsDTOExpected.setLastMonthPeriodRewardPoints(110L);
		rewardsDTOExpected.setLastSecondMonthPeriodRewardPoints(0L);
		rewardsDTOExpected.setLastThirdMonthPeriodRewardPoints(0L);
		rewardsDTOExpected.setTotalRewardPoints(110L);
		
		Assertions.assertEquals(rewardsDTOExpected, rewardsProgramService.getCustomerRewardsById(ID));
	}
	
	@Test
	public void getCustomerRewards_whenTransactionsDuringLastMonthPeriodOnly() {
		
		List<Transaction> transactions1 = Arrays.asList(new Transaction(1L,customer,now.atTime(currentTime),60d));
		
		Mockito.when(transactionReporisory.findByCustomerIdAndDateBetween(ID, lastMonthPeriod, now.atTime(currentTime)))
			.thenReturn(transactions1);
		Mockito.when(transactionReporisory.findByCustomerIdAndDateBetween(ID, lastSecondMonthPeriod, lastMonthPeriod))
			.thenReturn(new ArrayList<>());
		Mockito.when(transactionReporisory.findByCustomerIdAndDateBetween(ID, lastThirdMonthPeriod, lastSecondMonthPeriod))
			.thenReturn(new ArrayList<>());
		
		
		RewardsDTO rewardsDTOExpected = new RewardsDTO();
		rewardsDTOExpected.setCustomerId(customer.getId());
		rewardsDTOExpected.setLastMonthPeriodRewardPoints(10L);
		rewardsDTOExpected.setLastSecondMonthPeriodRewardPoints(0L);
		rewardsDTOExpected.setLastThirdMonthPeriodRewardPoints(0L);
		rewardsDTOExpected.setTotalRewardPoints(10L);
		
		Assertions.assertEquals(rewardsDTOExpected, rewardsProgramService.getCustomerRewardsById(ID));
	}
	
	@Test
	public void getCustomerRewards_whenTransactionsDuringLastSecondMonthPeriodOnly() {
		
		List<Transaction> transactions2 = Arrays.asList(new Transaction(2L,customer,lastMonthPeriod,120d));
		
		Mockito.when(transactionReporisory.findByCustomerIdAndDateBetween(ID, lastMonthPeriod, now.atTime(currentTime)))
			.thenReturn(new ArrayList<>());
		Mockito.when(transactionReporisory.findByCustomerIdAndDateBetween(ID, lastSecondMonthPeriod, lastMonthPeriod))
			.thenReturn(transactions2);
		Mockito.when(transactionReporisory.findByCustomerIdAndDateBetween(ID, lastThirdMonthPeriod, lastSecondMonthPeriod))
			.thenReturn(new ArrayList<>());
		
		
		RewardsDTO rewardsDTOExpected = new RewardsDTO();
		rewardsDTOExpected.setCustomerId(customer.getId());
		rewardsDTOExpected.setLastMonthPeriodRewardPoints(0L);
		rewardsDTOExpected.setLastSecondMonthPeriodRewardPoints(90L);
		rewardsDTOExpected.setLastThirdMonthPeriodRewardPoints(0L);
		rewardsDTOExpected.setTotalRewardPoints(90L);
		
		Assertions.assertEquals(rewardsDTOExpected, rewardsProgramService.getCustomerRewardsById(ID));
	}
	
	@Test
	public void getCustomerRewards_whenTransactionsDuringLastThirdMonthPeriodOnly() {
		
		List<Transaction> transactions3 = Arrays.asList(new Transaction(3L,customer,lastMonthPeriod,120d));
		
		Mockito.when(transactionReporisory.findByCustomerIdAndDateBetween(ID, lastMonthPeriod, now.atTime(currentTime)))
			.thenReturn(new ArrayList<>());
		Mockito.when(transactionReporisory.findByCustomerIdAndDateBetween(ID, lastSecondMonthPeriod, lastMonthPeriod))
			.thenReturn(new ArrayList<>());
		Mockito.when(transactionReporisory.findByCustomerIdAndDateBetween(ID, lastThirdMonthPeriod, lastSecondMonthPeriod))
			.thenReturn(transactions3);
		
		
		RewardsDTO rewardsDTOExpected = new RewardsDTO();
		rewardsDTOExpected.setCustomerId(customer.getId());
		rewardsDTOExpected.setLastMonthPeriodRewardPoints(0L);
		rewardsDTOExpected.setLastSecondMonthPeriodRewardPoints(0L);
		rewardsDTOExpected.setLastThirdMonthPeriodRewardPoints(90L);
		rewardsDTOExpected.setTotalRewardPoints(90L);
		
		Assertions.assertEquals(rewardsDTOExpected, rewardsProgramService.getCustomerRewardsById(ID));
	}
	
	@Test
	public void getCustomerRewards_whenTransactionsOutsideLastThreeMonthsPeriod() {
				
		Mockito.when(transactionReporisory.findByCustomerIdAndDateBetween(ID, lastMonthPeriod, now.atTime(currentTime)))
			.thenReturn(new ArrayList<>());
		Mockito.when(transactionReporisory.findByCustomerIdAndDateBetween(ID, lastSecondMonthPeriod, lastMonthPeriod))
			.thenReturn(new ArrayList<>());
		Mockito.when(transactionReporisory.findByCustomerIdAndDateBetween(ID, lastThirdMonthPeriod, lastSecondMonthPeriod))
			.thenReturn(new ArrayList<>());
		
		
		RewardsDTO rewardsDTOExpected = new RewardsDTO();
		rewardsDTOExpected.setCustomerId(customer.getId());
		rewardsDTOExpected.setLastMonthPeriodRewardPoints(0L);
		rewardsDTOExpected.setLastSecondMonthPeriodRewardPoints(0L);
		rewardsDTOExpected.setLastThirdMonthPeriodRewardPoints(0L);
		rewardsDTOExpected.setTotalRewardPoints(0L);
		
		Assertions.assertEquals(rewardsDTOExpected, rewardsProgramService.getCustomerRewardsById(ID));
	}
	
	@Test
	public void getCustomerRewardsById_whenOneTransactionDuringEveryLastMonthPeriod() {
		
		List<Transaction> transactions1 = Arrays.asList(new Transaction(1L,customer,now.atTime(currentTime),120d));
		List<Transaction> transactions2 = Arrays.asList(new Transaction(2L,customer,lastMonthPeriod,80d));
		List<Transaction> transactions3 = Arrays.asList(new Transaction(3L,customer,lastSecondMonthPeriod,70d));

		Mockito.when(transactionReporisory.findByCustomerIdAndDateBetween(ID, lastMonthPeriod, now.atTime(currentTime)))
			.thenReturn(transactions1);
		Mockito.when(transactionReporisory.findByCustomerIdAndDateBetween(ID, lastSecondMonthPeriod, lastMonthPeriod))
			.thenReturn(transactions2);
		Mockito.when(transactionReporisory.findByCustomerIdAndDateBetween(ID, lastThirdMonthPeriod, lastSecondMonthPeriod))
			.thenReturn(transactions3);
		
		RewardsDTO rewardsDTOExpected = new RewardsDTO();
		rewardsDTOExpected.setCustomerId(customer.getId());
		rewardsDTOExpected.setLastMonthPeriodRewardPoints(90L);
		rewardsDTOExpected.setLastSecondMonthPeriodRewardPoints(30L);
		rewardsDTOExpected.setLastThirdMonthPeriodRewardPoints(20L);
		rewardsDTOExpected.setTotalRewardPoints(140L);
		
		Assertions.assertEquals(rewardsDTOExpected, rewardsProgramService.getCustomerRewardsById(ID));
	}
}
