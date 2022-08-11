package com.cts.membermicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cts.membermicroservice.exception.ClaimNotFoundException;
import com.cts.membermicroservice.exception.MemberNotFoundException;
import com.cts.membermicroservice.exception.PolicyNotFoundException;
import com.cts.membermicroservice.exception.TokenExpireException;
import com.cts.membermicroservice.pojo.Bill;
import com.cts.membermicroservice.pojo.Claim;
import com.cts.membermicroservice.pojo.ClaimInput;
import com.cts.membermicroservice.service.MemberService;

@CrossOrigin
@RestController
public class MemberController {
	@Autowired
	MemberService service;

	@GetMapping("/viewbills")
	public Bill viewBills(@RequestParam("memberId") String memberId, @RequestParam("policyId") String policyId,
			@RequestHeader("Authorization") String token) throws MemberNotFoundException, TokenExpireException {
		return service.viewBills(memberId, policyId, token);
	}

	@GetMapping("/getclaimstatus/{claimId}")
	public Claim getClaimStatus(@PathVariable("claimId") Integer claimId) throws ClaimNotFoundException {
		return service.getClaimStatus(claimId);
	}

	@PostMapping(value = "/submitclaim")
	public Claim submitClaim(@RequestBody ClaimInput claim) throws PolicyNotFoundException {
		return service.submitClaim(claim);
	}
}
