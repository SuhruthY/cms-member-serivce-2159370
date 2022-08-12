package com.cts.membermicroservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cts.membermicroservice.client.AuthClient;
import com.cts.membermicroservice.client.ClaimClient;
import com.cts.membermicroservice.entity.Member;
import com.cts.membermicroservice.entity.MemberPremium;
import com.cts.membermicroservice.exception.ClaimNotFoundException;
import com.cts.membermicroservice.exception.MemberNotFoundException;
import com.cts.membermicroservice.exception.PolicyNotFoundException;
import com.cts.membermicroservice.exception.TokenExpireException;
import com.cts.membermicroservice.pojo.Bill;
import com.cts.membermicroservice.pojo.Claim;
import com.cts.membermicroservice.pojo.ClaimInput;
import com.cts.membermicroservice.pojo.ClaimStatusOutput;
import com.cts.membermicroservice.repository.MemberPremiumRepository;
import com.cts.membermicroservice.repository.MemberRepository;

@Service
public class MemberService {

	@Autowired
	MemberPremiumRepository memberPremiumRepo;

	@Autowired
	MemberRepository memberRepo;

	@Autowired
	ClaimClient claimClient;

	@Autowired
	AuthClient authClient;

	public Bill viewBills(String memberId, String policyId, String token)
			throws MemberNotFoundException, TokenExpireException {
		if (authClient.authorizeTheRequest(token)) {
			MemberPremium memberPremium = memberPremiumRepo.findByMemberIdAndPolicyId(memberId, policyId);

			if (memberPremium == null) {
				throw new MemberNotFoundException("Member not found");
			} else {
				Optional<Member> member = memberRepo.findById(memberId);
				if(!member.isPresent()) {
					throw new MemberNotFoundException("Member not found");
				}
				return Bill.builder().member(member.get()).policyId(memberPremium.getPolicyId())
						.lastPaidDate(memberPremium.getLastPaidDate()).dueDate(memberPremium.getDueDate())
						.premiumDue(memberPremium.getPremiumDue()).lateCharges(memberPremium.getLateCharges()).build();
			}
		} else {
			throw new TokenExpireException("Token is expired");
		}
	}

	public ClaimStatusOutput getClaimStatus(String claimId, String token) throws ClaimNotFoundException {
		ClaimStatusOutput claimStatusOutput = null;
		try {
			claimStatusOutput = claimClient.getClaimStatus(claimId, token);
		} catch (MissingRequestHeaderException | ClaimNotFoundException | TokenExpireException e) {
			e.printStackTrace();
		}
		if (claimStatusOutput == null)
			throw new ClaimNotFoundException("Claim not found");
		else
			return claimStatusOutput;
	}

	public Claim submitClaim(ClaimInput claimInput, String token) throws PolicyNotFoundException {
		Claim submitClaim;
		try {
			submitClaim = claimClient.submitClaim(claimInput, token);
			return submitClaim;
		} catch (PolicyNotFoundException | TokenExpireException e) {
			e.printStackTrace();
		}
		return null;
	}

}
