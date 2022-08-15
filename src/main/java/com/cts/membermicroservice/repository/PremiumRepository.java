package com.cts.membermicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.membermicroservice.entity.Premium;

/**
 * A repository class for storing member details in h2 database
 * @author SuhruthY
 */
@Repository
public interface PremiumRepository extends JpaRepository<Premium, String> {
	Premium findByMemberIdAndPolicyId(String memberId, String policyId);	
}
