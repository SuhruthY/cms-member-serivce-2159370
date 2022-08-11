package com.cts.membermicroservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import com.cts.membermicroservice.exception.ClaimNotFoundException;
import com.cts.membermicroservice.exception.PolicyNotFoundException;
import com.cts.membermicroservice.pojo.Claim;
import com.cts.membermicroservice.pojo.ClaimInput;

@FeignClient(url="${claim.url}",name="claimapp")
public interface ClaimClient {
	
	@GetMapping(value="/getclaimstatus/{claimId}")
	public Claim getClaimStatus(@PathVariable("claimId") Integer claimId) throws ClaimNotFoundException;

	@PostMapping(value="/submitclaim")
	public Claim submitClaim(@RequestBody ClaimInput claim) throws PolicyNotFoundException;
}
