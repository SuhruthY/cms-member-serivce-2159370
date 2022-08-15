package com.cts.membermicroservice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.cts.membermicroservice.entity.Member;
import com.cts.membermicroservice.entity.Premium;
import com.cts.membermicroservice.repository.MemberRepository;
import com.cts.membermicroservice.repository.PremiumRepository;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

/**
 * A class to load the initial data and starts the main method
 * @author SuhruthY
 */
@SpringBootApplication
@EnableFeignClients
public class MemberMicroserviceApplication {

	@Value("${cms-member-service.data.member.url}")
	private String memberDataUrl;

	@Value("#{'${cms-member-service.data.member.column_names}'.split(',')}")
	private List<String> memberDataColumnNames;
	
	@Value("${cms-member-service.data.premium.url}")
	private String premiumDataUrl;

	@Value("#{'${cms-member-service.data.premium.column_names}'.split(',')}")
	private List<String> premiumDataColumnNames;
	
	public static void main(String[] args) {
		SpringApplication.run(MemberMicroserviceApplication.class, args);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean
	CommandLineRunner run(MemberRepository memberRepo, PremiumRepository premiumRepo) {
		return args -> {

			HeaderColumnNameTranslateMappingStrategy mappingStrategy;
			
			// maps cms_member.csv headers to Member.class properties
			mappingStrategy = new HeaderColumnNameTranslateMappingStrategy();
			mappingStrategy.setColumnMapping(Map.of(
					memberDataColumnNames.get(0), "id", 
					memberDataColumnNames.get(1), "name", 
					memberDataColumnNames.get(2), "gender", 
					memberDataColumnNames.get(3), "age",
					memberDataColumnNames.get(4), "phno", 
					memberDataColumnNames.get(5), "email"));
			mappingStrategy.setType(Member.class);

			memberRepo.saveAll(new CsvToBeanBuilder(
					new BufferedReader(new InputStreamReader(new URL(memberDataUrl).openStream())))
					.withType(Member.class).withMappingStrategy(mappingStrategy).build().parse());
			
			// maps cms_member_premium.csv headers to Premium.class properties
			mappingStrategy = new HeaderColumnNameTranslateMappingStrategy();
			mappingStrategy.setColumnMapping(Map.of(
					premiumDataColumnNames.get(0), "id", 
					premiumDataColumnNames.get(1), "memberId", 
					premiumDataColumnNames.get(2), "policyId",
					premiumDataColumnNames.get(3), "lastPaidDate", 
					premiumDataColumnNames.get(4), "dueDate", 
					premiumDataColumnNames.get(5), "premiumDue",
					premiumDataColumnNames.get(6), "lateCharges"));
			mappingStrategy.setType(Premium.class);
			
			premiumRepo.saveAll(new CsvToBeanBuilder(
					new BufferedReader(new InputStreamReader(new URL(premiumDataUrl).openStream())))
					.withType(Member.class).withMappingStrategy(mappingStrategy).build().parse());

		};
	}

}
