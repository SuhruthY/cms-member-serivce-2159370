package com.cts.MemberMicroservice.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.cts.membermicroservice.client.AuthClient;
import com.cts.membermicroservice.service.MemberService;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	MemberService service;
	
	@MockBean
	AuthClient authClient;

	@Test
	void notNull() {
		assertThat(service).isNotNull();
	}

	@Test
	void notNull1() {
		assertThat(mockMvc).isNotNull();
	}

	@Test
	void testViewBills() throws Exception {
		when(authClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);
		this.mockMvc.perform(get("/viewbills/{memberId}/{policyId}", 1, 2).header("Authorization", "@uthoriz@tionToken123"))
				.andExpect(status().isOk());
	}

	@Test
	void testGetClaimStatus() throws Exception {
		this.mockMvc.perform(get("/getclaimstatus/{claimId}", 10)).andExpect(status().isOk());
	}

	@Test
	void testSubmitClaim() throws Exception {
		this.mockMvc.perform(post("/submitclaim")).andExpect(status().isBadRequest());
	}

}
