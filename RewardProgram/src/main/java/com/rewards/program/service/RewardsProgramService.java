package com.rewards.program.service;

import com.rewards.program.dtos.RewardsDTO;

/**
 *
 * @author Ali BEN BRAHIM
 *
 */
public interface RewardsProgramService {
    RewardsDTO getCustomerRewardsById(Long customerId);
}
