package com.cts.membermicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.membermicroservice.entity.Member;

/**
 * A repository class for storing member details in h2 database
 * @author SuhruthY
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, String>{

}
