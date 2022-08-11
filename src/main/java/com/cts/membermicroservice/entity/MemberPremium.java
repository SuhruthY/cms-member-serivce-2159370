package com.cts.membermicroservice.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table (name="MemberPremium")
public class MemberPremium {
	@Id
	@GeneratedValue
	private int id;
	private String memberId;
	private String policyId;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date lastPaidDate;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dueDate;
	private int premiumDue;
	private int lateCharges;
}
