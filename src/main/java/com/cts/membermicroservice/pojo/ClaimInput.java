package com.cts.membermicroservice.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClaimInput {
	
	public String policyId;
	public String claimBenefit;
	public String hospitalId;
	public Integer benefitAvailed;
	public Integer amtClaimed;
	public String memberId;
	private String benefitId;

}