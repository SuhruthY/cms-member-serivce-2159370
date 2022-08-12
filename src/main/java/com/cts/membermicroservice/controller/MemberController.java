package com.cts.membermicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.cts.membermicroservice.exception.TokenExpireException;
import com.cts.membermicroservice.pojo.Bill;
import com.cts.membermicroservice.pojo.Claim;
import com.cts.membermicroservice.pojo.ClaimInput;
import com.cts.membermicroservice.pojo.ClaimStatusOutput;
import com.cts.membermicroservice.service.MemberService;

@CrossOrigin
@RestController
public class MemberController {
	@Autowired
	MemberService service;

	@GetMapping("/viewBills/{memberId}/{policyId}")
	public Bill viewBills(@PathVariable("memberId") String memberId, @PathVariable("policyId") String policyId,
			@RequestHeader("Authorization") String token) throws MemberNotFoundException, TokenExpireException {
		return service.viewBills(memberId, policyId, token);
	}

	@GetMapping("/getClaimStatus/{claimId}")
	public ClaimStatusOutput getClaimStatus(@PathVariable("claimId") String claimId,
			@RequestHeader("Authorization") String token)
			throws ClaimNotFoundException, TokenExpireException, MissingRequestHeaderException {
		return service.getClaimStatus(claimId, token);
	}

	@PostMapping(value = "/submitClaim")
	public Claim submitClaim(@RequestBody ClaimInput claim, @RequestHeader("Authorization") String token)
			throws PolicyNotFoundException, TokenExpireException {
		return service.submitClaim(claim, token);
	}
}
