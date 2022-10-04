package com.rewards.program.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rewards.program.dtos.TransactionDTO;
import com.rewards.program.service.TransactionService;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Ali BEN BRAHIM
 *
 */
@RestController
@RequestMapping("/transaction")
@Slf4j
public class TransactionController {
    
    @Autowired
    TransactionService transactionService;
    
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionDTO> addTransaction(@RequestBody TransactionDTO transactionDTO) {
    	log.info("Adding new transaction");
    	return new ResponseEntity<>(transactionService.addTransaction(transactionDTO),HttpStatus.OK);	
    }

    @GetMapping(value = "/{transactionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionDTO> getTransaction(@PathVariable Long transactionId){
    	log.info("Searching transaction");
    	return new ResponseEntity<>(transactionService.findById(transactionId), HttpStatus.OK);
    }
}
