package com.cts.membermicroservice.pojo;

import java.util.Date;

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
public class ErrorResponse {
	private Integer statusCode;
	private String statusMsg;
	private Date statusDate;

}
