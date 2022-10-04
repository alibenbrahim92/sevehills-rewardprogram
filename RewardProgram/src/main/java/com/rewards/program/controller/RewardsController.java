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

import com.rewards.program.dtos.CustomerDTO;
import com.rewards.program.dtos.RewardsDTO;
import com.rewards.program.repository.CustomerRepository;
import com.rewards.program.service.CustomerService;
import com.rewards.program.service.RewardsProgramService;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Ali BEN BRAHIM
 *
 */
@RestController
@RequestMapping("/customer")
@Slf4j
public class RewardsController {

    @Autowired
    RewardsProgramService rewardsProgramService;
    
    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;
  

    @GetMapping(value = "/{customerId}/rewardPoints",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RewardsDTO> getCustomerRewardsById(@PathVariable("customerId") Long customerId){
        log.info("Calculating customer rewards");
    	customerService.findCustomerByID(customerId);
        RewardsDTO customerRewards = rewardsProgramService.getCustomerRewardsById(customerId);
        return new ResponseEntity<>(customerRewards,HttpStatus.OK);
    }
    
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> addCustomer(@RequestBody CustomerDTO customerDTO) {
    	log.info("Adding new customer");
    	return new ResponseEntity<>(customerService.addCustomer(customerDTO),HttpStatus.OK);	
    }

}
