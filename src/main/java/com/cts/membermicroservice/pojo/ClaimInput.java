package com.cts.membermicroservice.pojo;

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
public class ClaimInput {
	
	private String policyId;
	private String claimBenefit;
	private String hospitalId;
	private Integer benefitAvailed;
	private Integer amtClaimed;
	private String memberId;
	private String benefitId;

}