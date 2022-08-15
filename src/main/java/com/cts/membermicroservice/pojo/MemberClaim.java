package com.cts.membermicroservice.pojo;

import com.cts.membermicroservice.entity.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * A pojo class for retrieving values
 * @author SuhruthY
 */
@Setter
@Getter
@Builder
public class MemberClaim {
	private Member member;
	private String claimStatus;
	private String remarks;
}
