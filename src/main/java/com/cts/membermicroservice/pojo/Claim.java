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
	private String memberId;

}