package com.rewards.program.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Ali BEN BRAHIM
 *
 */
@Entity
@Table(name = "TRANSACTION")
@Data @NoArgsConstructor @AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer customer;

    @Column
    private LocalDateTime date;

    @Column
    private double amount;
}
