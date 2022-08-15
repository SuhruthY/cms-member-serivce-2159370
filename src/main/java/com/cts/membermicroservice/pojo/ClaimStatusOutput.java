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
public class ClaimStatusOutput {
	private String claimStatus;
	private String remarks;
}