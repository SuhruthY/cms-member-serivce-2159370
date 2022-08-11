package com.cts.membermicroservice.pojo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.cts.membermicroservice.entity.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Bill {
	private Member member;
	private String policyId;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date lastPaidDate;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dueDate;
	private int premiumDue;
	private int lateCharges;
}
