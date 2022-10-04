package com.rewards.program.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Ali BEN BRAHIM
 *
 */
@Data @NoArgsConstructor @AllArgsConstructor
public class RewardsDTO {
    private Long customerId;
	private Long lastMonthPeriodRewardPoints;
    private Long lastSecondMonthPeriodRewardPoints;
    private Long lastThirdMonthPeriodRewardPoints;
    private Long totalRewardPoints;
}
