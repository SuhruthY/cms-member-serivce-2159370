package com.cts.membermicroservice.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Claim {

	private String claimId;
	private String status;
	private String remarks;
	private Integer benefitAvailed;
	private Integer amtClaimed;
	private boolean isSettled;
	private String policyId;
	private String policyBenefits;
	private String hospitalId;
	public String memberId;

}