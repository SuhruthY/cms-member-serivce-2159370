package com.cts.membermicroservice.controller;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cts.membermicroservice.exception.ClaimNotFoundException;
import com.cts.membermicroservice.exception.MemberNotFoundException;
import com.cts.membermicroservice.exception.PolicyNotFoundException;
import com.cts.membermicroservice.exception.PremiumNotFoundException;
import com.cts.membermicroservice.exception.TokenExpireException;
import com.cts.membermicroservice.pojo.Claim;
import com.cts.membermicroservice.pojo.ClaimInput;
import com.cts.membermicroservice.pojo.MemberClaim;
import com.cts.membermicroservice.pojo.MemberPremium;
import com.cts.membermicroservice.service.MemberService;

import lombok.extern.slf4j.Slf4j;

/**
 * A class for controlling request mappings
 * @author SuhruthY
 */
@CrossOrigin
@RestController
@Slf4j
public class MemberController {
	@Autowired
	MemberService service;
	
	/**
	 * This request mapping is for viewing bill details of specific member and policy
	 * @param memberId - unique string to identify a member
	 * @param policyId - unique string to identify a policy
	 * @param token - jwt to verify
	 * @return bill with member details - status code 200
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws PremiumNotFoundException
	 * @throws TokenExpireException
	 * @throws MemberNotFoundException
	 */
	@GetMapping("/viewBills/{memberId}/{policyId}")
	public ResponseEntity<MemberPremium> viewBills(@PathVariable("memberId") String memberId,
			@PathVariable("policyId") String policyId, @RequestHeader("Authorization") String token)
			throws IllegalAccessException, InvocationTargetException, PremiumNotFoundException, TokenExpireException,
			MemberNotFoundException {
		log.info("MemberController.viewBills: returns bill details of a member by policyId");
		return ResponseEntity.ok(service.viewBills(memberId, policyId, token));
	}
	
	/**
	 * This request mapping is for retrieving status of specific claim
	 * @param memberId - unique string to identify a member
	 * @param claimId - unique string to identify a claim
	 * @param token - jwt to verify
	 * @return claim status with member details - status code 200
	 * @throws MissingRequestHeaderException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws ClaimNotFoundException
	 * @throws TokenExpireException
	 * @throws MemberNotFoundException
	 */
	@GetMapping("/getClaimStatus/{memberId}/{claimId}")
	public ResponseEntity<MemberClaim> getClaimStatus(@PathVariable("memberId") String memberId,
			@PathVariable("claimId") String claimId, @RequestHeader("Authorization") String token)
			throws MissingRequestHeaderException, IllegalAccessException, InvocationTargetException,
			ClaimNotFoundException, TokenExpireException, MemberNotFoundException {
		log.info("MemberController.getClaimStatus: returns claim status with member details");
		return ResponseEntity.ok(service.getClaimStatus(claimId, memberId, token));
	}
	
	/**
	 * This request mapping is for creating a new claim
	 * @param claim - calim details as request body
	 * @param token - jwt to verify
	 * @return new claim created - status code 201
	 * @throws PolicyNotFoundException
	 * @throws TokenExpireException
	 */
	@PostMapping(value = "/submitClaim")
	public ResponseEntity<Claim> submitClaim(@RequestBody ClaimInput claim,
			@RequestHeader("Authorization") String token) throws PolicyNotFoundException, TokenExpireException {
		log.info("MemberController.submitClaim: creates a new claim with specified details");
		return ResponseEntity.status(HttpStatus.CREATED).body(service.submitClaim(claim, token));
	}
}
