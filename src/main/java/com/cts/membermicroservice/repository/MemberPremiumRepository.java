package com.cts.membermicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.membermicroservice.entity.MemberPremium;

@Repository
public interface MemberPremiumRepository extends JpaRepository<MemberPremium, String> {
	MemberPremium findByMemberIdAndPolicyId(String memberId, String policyId);

	MemberPremium findByMemberId(String memberId);
}
