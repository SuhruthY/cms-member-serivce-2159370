package com.cts.membermicroservice.pojo;

import java.util.Date;

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
public class MemberPremium {
	private Member member;
	private String policyId;
	private Date lastPaidDate;
	private Date dueDate;
	private int premiumDue;
	private int lateCharges;
}
