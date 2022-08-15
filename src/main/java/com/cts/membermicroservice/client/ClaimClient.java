package com.cts.membermicroservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cts.membermicroservice.exception.ClaimNotFoundException;
import com.cts.membermicroservice.exception.PolicyNotFoundException;
import com.cts.membermicroservice.exception.TokenExpireException;
import com.cts.membermicroservice.pojo.Claim;
import com.cts.membermicroservice.pojo.ClaimInput;
import com.cts.membermicroservice.pojo.ClaimStatusOutput;

/**
 * A proxy class for calling cms-claim-service
 * @author SuhruthY
 */
@FeignClient(url = "${claim.url}", name = "cms-claim-service")
public interface ClaimClient {
	/**
	 * This method gives the status for a given claim
	 * @param claimId - unique string to identify a claim
	 * @param token - jwt to verify
	 * @return claim status and description parameters
	 * @throws ClaimNotFoundException
	 * @throws TokenExpireException
	 * @throws MissingRequestHeaderException
	 */
	@GetMapping("/getClaimStatus/{claimId}")
	public ClaimStatusOutput getClaimStatus(@PathVariable("claimId") String claimId,
			@RequestHeader("Authorization") String token)
			throws ClaimNotFoundException, TokenExpireException, MissingRequestHeaderException;
	
	/**
	 * This method creates new claim 
	 * @param claim - calim details as request body
	 * @param token - jwt to verify
	 * @return created claim 
	 * @throws PolicyNotFoundException
	 * @throws TokenExpireException
	 */
	@PostMapping("/submitClaim")
	public Claim submitClaim(@RequestBody ClaimInput claim, @RequestHeader("Authorization") String token)
			throws PolicyNotFoundException, TokenExpireException;
}
