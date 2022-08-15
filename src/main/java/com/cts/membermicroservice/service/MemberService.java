package com.cts.membermicroservice.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MissingRequestHeaderException;

import com.cts.membermicroservice.client.AuthClient;
import com.cts.membermicroservice.client.ClaimClient;
import com.cts.membermicroservice.entity.Member;
import com.cts.membermicroservice.entity.Premium;
import com.cts.membermicroservice.exception.ClaimNotFoundException;
import com.cts.membermicroservice.exception.MemberNotFoundException;
import com.cts.membermicroservice.exception.PolicyNotFoundException;
import com.cts.membermicroservice.exception.PremiumNotFoundException;
import com.cts.membermicroservice.exception.TokenExpireException;
import com.cts.membermicroservice.pojo.Claim;
import com.cts.membermicroservice.pojo.ClaimInput;
import com.cts.membermicroservice.pojo.ClaimStatusOutput;
import com.cts.membermicroservice.pojo.MemberClaim;
import com.cts.membermicroservice.pojo.MemberPremium;
import com.cts.membermicroservice.repository.MemberRepository;
import com.cts.membermicroservice.repository.PremiumRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * A service class for defining all util methods for controller
 * @author SuhruthY
 */
@Service
@Slf4j
public class MemberService {
	
	private static final String MESSAGE_MEMBER_NOT_FOUND="Member not found";
	private static final String MESSAGE_PREMIUM_NOT_FOUND="Premium not found";
	private static final String MESSAGE_CLAIM_NOT_FOUND="Claim not found";
	private static final String MESSAGE_TOKEN_EXPIRED="Token is expired";

	@Autowired
	private PremiumRepository premiumRepo;

	@Autowired
	private MemberRepository memberRepo;

	@Autowired
	private ClaimClient claimClient;

	@Autowired
	private AuthClient authClient;
	
	/**
	 * This method is for viewing bill details of specific member and policy
	 * @param memberId - unique string to identify a member
	 * @param policyId - unique string to identify a policy
	 * @param token - jwt to verify
	 * @return bill with member details 
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws PremiumNotFoundException
	 * @throws TokenExpireException
	 * @throws MemberNotFoundException
	 */
	public MemberPremium viewBills(String memberId, String policyId, String token)
			throws PremiumNotFoundException, TokenExpireException, IllegalAccessException, InvocationTargetException, MemberNotFoundException {
		log.info("START MemberService.viewBills");
		if (!authClient.authorizeTheRequest(token)) {
			throw new TokenExpireException(MESSAGE_TOKEN_EXPIRED);
		}
		
		Premium premium = premiumRepo.findByMemberIdAndPolicyId(memberId, policyId);
		if (premium == null) {
			throw new PremiumNotFoundException(MESSAGE_PREMIUM_NOT_FOUND);
		} 

		MemberPremium memberPremium = MemberPremium.builder().build();
		BeanUtils.copyProperties(memberPremium, premium);
		Optional<Member> member = memberRepo.findById(memberId);
		
		if(!member.isPresent()) {
			throw new MemberNotFoundException(MESSAGE_MEMBER_NOT_FOUND);
		}
		
		memberPremium.setMember(member.get());
		log.info("END MemberService.viewBills");
		return memberPremium;
	}
	
	/**
	 * This method is for retrieving status of specific claim
	 * @param memberId - unique string to identify a member
	 * @param claimId - unique string to identify a claim
	 * @param token - jwt to verify
	 * @return claim status with member details
	 * @throws MissingRequestHeaderException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws ClaimNotFoundException
	 * @throws TokenExpireException
	 * @throws MemberNotFoundException
	 */
	public MemberClaim getClaimStatus(String claimId, String memberId, String token)
			throws MissingRequestHeaderException, ClaimNotFoundException, TokenExpireException, IllegalAccessException,
			InvocationTargetException, MemberNotFoundException {
		log.info("START MemberService.getClaimStatus");
		ClaimStatusOutput claimStatusOutput = claimClient.getClaimStatus(claimId, token);
		if (claimStatusOutput == null) {
			throw new ClaimNotFoundException(MESSAGE_CLAIM_NOT_FOUND);
		} 
		
		MemberClaim memberClaim = MemberClaim.builder().build();
		BeanUtils.copyProperties(memberClaim, claimStatusOutput);
		Optional<Member> member = memberRepo.findById(memberId);
		
		if(!member.isPresent()) {
			throw new MemberNotFoundException(MESSAGE_MEMBER_NOT_FOUND);
		}
		
		memberClaim.setMember(member.get());
		log.info("END MemberService.getClaimStatus");
		return memberClaim;
	}
	
	/**
	 * This method is for creating a new claim
	 * @param claim - calim details as request body
	 * @param token - jwt to verify
	 * @return new claim created
	 * @throws PolicyNotFoundException
	 * @throws TokenExpireException
	 */
	public Claim submitClaim(ClaimInput claimInput, String token) throws PolicyNotFoundException, TokenExpireException {
		log.info("START MemberService.submitClaim");
		log.info("END MemberService.submitClaim");
		return claimClient.submitClaim(claimInput, token);
	}

}
