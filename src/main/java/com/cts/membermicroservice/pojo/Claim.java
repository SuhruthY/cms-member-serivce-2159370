package com.cts.membermicroservice.pojo;


import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Claim {
	
	private Integer claimNo;
	private String status;
	private String remarks;
	private String policyBenefits;
	private String hospitalName;
	private Integer benefitsAvailed;
	private Integer amount;
	private Integer policyId;
	private String policyName;

}
