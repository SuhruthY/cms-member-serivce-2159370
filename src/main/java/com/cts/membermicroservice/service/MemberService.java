package com.cts.membermicroservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public Claim getClaimStatus(Integer claimId) throws ClaimNotFoundException {
		Claim claim = claimClient.getClaimStatus(claimId);
		if (claim == null)
			throw new ClaimNotFoundException("Claim not found");
		else
			return claim;
	}

	public Claim submitClaim(ClaimInput claimInput) throws PolicyNotFoundException {
		Claim claim = claimClient.submitClaim(claimInput);
		return claim;
	}

}
