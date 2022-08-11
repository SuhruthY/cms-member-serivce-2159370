package com.cts.membermicroservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.cts.membermicroservice.service.CustomIdGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="Member")
public class Member {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq")
    @GenericGenerator(
        name = "member_seq", 
        strategy = "com.cts.membermicroservice.service.CustomIdGenerator", 
        parameters = {
            @Parameter(name = CustomIdGenerator.INCREMENT_PARAM, value = "1"),
            @Parameter(name = CustomIdGenerator.VALUE_PREFIX_PARAMETER, value = "CMS_M"),
            @Parameter(name = CustomIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%03d") })
	private String id;
	private String name;
	private String gender;
	private int age;
	private long phno;
	private String email;

}