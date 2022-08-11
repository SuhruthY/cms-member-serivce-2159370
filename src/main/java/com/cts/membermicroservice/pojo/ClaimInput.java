package com.cts.membermicroservice.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClaimInput {

	private String policyBenefits;
	private String hospitalName;
	private Integer benefitsAvailed;
	private Integer amount;
	private Integer policyId;
	private String policyName;

}