package com.cts.membermicroservice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;

import com.cts.membermicroservice.entity.Member;
import com.cts.membermicroservice.entity.MemberPremium;
import com.cts.membermicroservice.repository.MemberPremiumRepository;
import com.cts.membermicroservice.repository.MemberRepository;

@SpringBootApplication
@EnableFeignClients
public class MemberMicroserviceApplication {

	@Autowired
	private ResourceLoader resourceLoader;

	public static void main(String[] args) {
		SpringApplication.run(MemberMicroserviceApplication.class, args);
	}

	@Bean
	CommandLineRunner run(MemberRepository memberRepo, MemberPremiumRepository memberPremiumRepo) {
		return (args) -> {
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			try (BufferedReader br = new BufferedReader(
					new InputStreamReader(resourceLoader.getResource("classpath:/cms_member.csv").getInputStream()))) {
				String line;
				while ((line = br.readLine()) != null) {
					String[] values = line.split(",");
					memberRepo.save(Member.builder()
							.name(values[0])
							.gender(values[1])
							.age(Integer.parseInt(values[2]))
							.phno(Long.parseLong(values[3]))
							.email(values[4]).build());
				}
			}
			
			try (BufferedReader br = new BufferedReader(
					new InputStreamReader(resourceLoader.getResource("classpath:/cms_member_premium.csv").getInputStream()))) {
				String line;
				while ((line = br.readLine()) != null) {
					String[] values = line.split(",");
					memberPremiumRepo.save(MemberPremium.builder()
							.memberId(values[0]).policyId(values[1])
							.lastPaidDate(dateFormat.parse(values[2]))
							.dueDate(dateFormat.parse(values[3]))
							.premiumDue(Integer.parseInt(values[4]))
							.lateCharges(Integer.parseInt(values[5])).build());
				}
			}
			
		};
	}

}
