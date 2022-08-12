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

@FeignClient(url = "${claim.url}", name = "claimapp")
public interface ClaimClient {

	@GetMapping(value = "/getClaimStatus/{claimId}")
	public ClaimStatusOutput getClaimStatus(@PathVariable("claimId") String claimId,
			@RequestHeader("Authorization") String token)
			throws ClaimNotFoundException, TokenExpireException, MissingRequestHeaderException;

	@PostMapping(value = "/submitClaim")
	public Claim submitClaim(@RequestBody ClaimInput claim, @RequestHeader("Authorization") String token)
			throws PolicyNotFoundException, TokenExpireException;
}
