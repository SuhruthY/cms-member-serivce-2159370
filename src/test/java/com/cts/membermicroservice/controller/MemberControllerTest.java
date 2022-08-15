package com.cts.membermicroservice.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.cts.membermicroservice.exception.ClaimNotFoundException;
import com.cts.membermicroservice.exception.MemberNotFoundException;
import com.cts.membermicroservice.exception.PremiumNotFoundException;
import com.cts.membermicroservice.exception.TokenExpireException;
import com.cts.membermicroservice.pojo.Claim;
import com.cts.membermicroservice.pojo.ClaimInput;
import com.cts.membermicroservice.pojo.MemberClaim;
import com.cts.membermicroservice.pojo.MemberPremium;
import com.cts.membermicroservice.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A class to test the MemberController.class
 * @author SuhruthY
 */
@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {
	
	private static final String ENDPOINT_VIEW_BILLS="/viewBills";
	private static final String ENDPOINT_GET_CLAIM_STATUS="/getClaimStatus";
	private static final String ENDPOINT_SUBMIT_CLAIM="/submitClaim";
	
	@Value("${cms-member-service.test.auth-header-name}")
	private String authHeaderName;
	
	@Value("${cms-member-service.test.auth-token}")
	private String authToken;
	
	@Value("${cms-member-service.test.member-id}")
	private String memberId;
	
	@Value("${cms-member-service.test.policy-id}")
	private String policyId;
	
	@Value("${cms-member-service.test.claim-id}")
	private String claimId;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MemberService service;

	@Test
	void notNull() {
		assertThat(service).isNotNull();
	}
	
	/**
	 * A method to test /viewBills mapping works as expected
	 * @throws Exception
	 */
	@Test
	void testViewBills() throws Exception {

		MemberPremium expected = MemberPremium.builder().build();
		when(service.viewBills(memberId, policyId, authToken)).thenReturn(expected);

		mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_VIEW_BILLS + "/" + memberId + "/" + policyId)
				.header(authHeaderName, authToken)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expected)))
				.andDo(MockMvcResultHandlers.print());

	}
	
	/**
	 * A method to test /viewBills throws TokenExpireException
	 * @throws Exception
	 */
	@Test
	void testViewBillsThrowsTokenExpireException() throws Exception {

		when(service.viewBills(memberId, policyId, authToken)).thenThrow(TokenExpireException.class);

		mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_VIEW_BILLS + "/" + memberId + "/" + policyId)
				.header(authHeaderName, authToken))
				.andExpect(MockMvcResultMatchers.status().isUnauthorized()).andDo(MockMvcResultHandlers.print());

	}
	
	/**
	 * A method to test /viewBills throws PremiumNotFoundException
	 * @throws Exception
	 */
	@Test
	void testViewBillsThrowsPremiumNotFoundException() throws Exception {

		when(service.viewBills(memberId, policyId, authToken))
				.thenThrow(PremiumNotFoundException.class);

		mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_VIEW_BILLS + "/" + memberId + "/" + policyId)
				.header(authHeaderName, authToken))
				.andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(MockMvcResultHandlers.print());

	}	
	
	/**
	 * A method to test /viewBills throws MemberNotFoundException
	 * @throws Exception
	 */
	@Test
	void testViewBillsThrowsMemberNotFoundException() throws Exception {

		when(service.viewBills(memberId, policyId, authToken)).thenThrow(MemberNotFoundException.class);

		mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_VIEW_BILLS + "/" + memberId + "/" + policyId)
				.header(authHeaderName, authToken))
				.andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(MockMvcResultHandlers.print());

	}
	
	/**
	 * A method to test /viewBills throws InternalServerError
	 * @throws Exception
	 */
	@Test
	void testViewBillsThrowsInternalServerError() throws Exception {

		when(service.viewBills(memberId, policyId, authToken)).thenThrow(RuntimeException.class);

		mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_VIEW_BILLS + "/" + memberId + "/" + policyId)
				.header(authHeaderName, authToken))
				.andExpect(MockMvcResultMatchers.status().isInternalServerError()).andDo(MockMvcResultHandlers.print());

	}
	
	/**
	 * A method to test /getClaimStatus mapping works as expected
	 * @throws Exception
	 */
	@Test
	void testGetClaimStatus() throws Exception {

		MemberClaim expected = MemberClaim.builder().build();
		when(service.getClaimStatus(claimId, memberId, authToken)).thenReturn(expected);

		mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_GET_CLAIM_STATUS + "/" + memberId + "/" + claimId)
				.header(authHeaderName,  authToken)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expected)))
				.andDo(MockMvcResultHandlers.print());

	}
	
	/**
	 * A method to test /getClaimStatus throws ClaimNotFoundException
	 * @throws Exception
	 */
	@Test
	void testGetClaimStatusThrowsClaimNotFoundException() throws Exception {

		when(service.getClaimStatus(claimId, memberId,  authToken))
				.thenThrow(ClaimNotFoundException.class);

		mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_GET_CLAIM_STATUS + "/" + memberId + "/" + claimId)
				.header(authHeaderName,  authToken))
				.andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(MockMvcResultHandlers.print());

	}
	
	/**
	 * A method to test /getClaimStatus throws MemberNotFoundException
	 * @throws Exception
	 */
	@Test
	void testGetClaimStatusThrowsMemberNotFoundException() throws Exception {

		when(service.getClaimStatus(claimId, memberId,  authToken))
				.thenThrow(MemberNotFoundException.class);

		mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_GET_CLAIM_STATUS + "/" + memberId + "/" + claimId)
				.header(authHeaderName,  authToken))
				.andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(MockMvcResultHandlers.print());

	}
	
	/**
	 * A method to test /getClaimStatus throws InternalServerError
	 * @throws Exception
	 */
	@Test
	void testGetClaimStatusThrowsInternalServerError() throws Exception {

		when(service.getClaimStatus(claimId, memberId,  authToken))
				.thenThrow(RuntimeException.class);

		mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_GET_CLAIM_STATUS + "/" + memberId + "/" + claimId)
				.header(authHeaderName,  authToken))
				.andExpect(MockMvcResultMatchers.status().isInternalServerError()).andDo(MockMvcResultHandlers.print());

	}
		
	/**
	 * A method to test /getClaimStatus throws TokenExpireExceptio
	 * @throws Exception
	 */
	@Test
	void testGetClaimStatusThrowsTokenExpireException() throws Exception {

		when(service.getClaimStatus(claimId, memberId,  authToken))
				.thenThrow(TokenExpireException.class);

		mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_GET_CLAIM_STATUS + "/" + memberId + "/" + claimId)
				.header(authHeaderName,  authToken))
				.andExpect(MockMvcResultMatchers.status().isUnauthorized()).andDo(MockMvcResultHandlers.print());

	}
	
	/**
	 * A method to test /submitClaim mapping works as expected
	 * @throws Exception
	 */
	@Test
	void testsubmitClaim() throws Exception {

		Claim expected = Claim.builder().build();
		ClaimInput requestBody = ClaimInput.builder().build();
		when(service.submitClaim(requestBody,  authToken)).thenReturn(expected);

		mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT_SUBMIT_CLAIM).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(requestBody))
				.header(authHeaderName,  authToken)).andExpect(MockMvcResultMatchers.status().isCreated())
				.andDo(MockMvcResultHandlers.print());

	}

}
