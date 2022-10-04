package com.rewards.program.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Ali BEN BRAHIM
 *
 */
@Data @AllArgsConstructor @NoArgsConstructor
public class TransactionDTO {

    private Long id;
    private CustomerDTO customerDTO;
    private LocalDateTime date;
    private double amount;
}
