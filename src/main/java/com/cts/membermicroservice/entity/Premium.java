package com.cts.membermicroservice.entity;

import static com.cts.membermicroservice.service.CustomIdGenerator.NUMBER_FORMAT_PARAMETER;
import static com.cts.membermicroservice.service.CustomIdGenerator.VALUE_PREFIX_PARAMETER;
import static org.hibernate.id.enhanced.SequenceStyleGenerator.INCREMENT_PARAM;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.opencsv.bean.CsvDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A entity class for determine type of storage
 * @author SuhruthY
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Premium")
public class Premium {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_premium_seq")
	@GenericGenerator(name = "member_premium_seq", strategy = "com.cts.membermicroservice.service.CustomIdGenerator", parameters = {
			@Parameter(name = INCREMENT_PARAM, value = "1"),
			@Parameter(name = VALUE_PREFIX_PARAMETER, value = "CMS_B"),
			@Parameter(name = NUMBER_FORMAT_PARAMETER, value = "%03d") })
	private String id;
	private String memberId;
	private String policyId;
	@CsvDate(value = "dd/MM/yyyy")
	private Date lastPaidDate;
	@CsvDate(value = "dd/MM/yyyy")
	private Date dueDate;
	private int premiumDue;
	private int lateCharges;
}
