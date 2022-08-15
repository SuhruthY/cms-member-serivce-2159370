package com.cts.membermicroservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.MissingRequestHeaderException;

import com.cts.membermicroservice.client.AuthClient;
import com.cts.membermicroservice.client.ClaimClient;
import com.cts.membermicroservice.entity.Member;
import com.cts.membermicroservice.entity.Premium;
import com.cts.membermicroservice.exception.ClaimNotFoundException;
import com.cts.membermicroservice.exception.MemberNotFoundException;
import com.cts.membermicroservice.exception.PolicyNotFoundException;
import com.cts.membermicroservice.exception.PremiumNotFoundException;
import com.cts.membermicroservice.exception.TokenExpireException;
import com.cts.membermicroservice.pojo.Claim;
import com.cts.membermicroservice.pojo.ClaimInput;
import com.cts.membermicroservice.pojo.ClaimStatusOutput;
import com.cts.membermicroservice.pojo.MemberClaim;
import com.cts.membermicroservice.pojo.MemberPremium;
import com.cts.membermicroservice.repository.MemberRepository;
import com.cts.membermicroservice.repository.PremiumRepository;

/**
 * A class to test the MemberService.class
 * @author SuhruthY
 */
class MemberServiceTest {

	@Value("${cms-member-service.test.auth-token}")
	private String authToken;

	@Value("${cms-member-service.test.member-id}")
	private String memberId;

	@Value("${cms-member-service.test.policy-id}")
	private String policyId;

	@Value("${cms-member-service.test.claim-id}")
	private String claimId;

	@Mock
	private PremiumRepository premiumRepo;

	@Mock
	private MemberRepository memberRepo;

	@Mock
	private ClaimClient claimClient;

	@Mock
	private AuthClient authClient;

	@InjectMocks
	private MemberService service;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * A method to test view_bills method
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws MemberNotFoundException
	 * @throws PremiumNotFoundException
	 * @throws TokenExpireException
	 */
	@Test
	void testViewBills() throws IllegalAccessException, InvocationTargetException, MemberNotFoundException,
			PremiumNotFoundException, TokenExpireException {

		when(authClient.authorizeTheRequest(authToken)).thenReturn(true);

		Premium premium = new Premium();
		Member member = new Member();
		when(premiumRepo.findByMemberIdAndPolicyId(memberId, policyId)).thenReturn(premium);
		when(memberRepo.findById(memberId)).thenReturn(Optional.of(member));

		MemberPremium expected = MemberPremium.builder().member(member).build();
		BeanUtils.copyProperties(expected, premium);
		MemberPremium actual = service.viewBills(memberId, policyId, authToken);

		assertThat(actual).isInstanceOf(MemberPremium.class);
		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}
	
	/**
	 * A method to test view_bills throws PremiumNotFoundException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@Test
	void testViewBillsThrowsPremiumNotFoundException() throws IllegalAccessException, InvocationTargetException {

		when(authClient.authorizeTheRequest(authToken)).thenReturn(true);

		Premium premium = new Premium();
		Member member = new Member();
		when(premiumRepo.findByMemberIdAndPolicyId(memberId, policyId)).thenReturn(premium);
		when(memberRepo.findById(memberId)).thenReturn(Optional.of(member));

		MemberPremium expected = MemberPremium.builder().member(member).build();
		BeanUtils.copyProperties(expected, premium);

		assertThrows(PremiumNotFoundException.class, () -> {
			service.viewBills(memberId, "wrongPolicyId", authToken);
		});
	}
	
	/**
	 * A method to test view_bills throws MemberNotFoundException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@Test
	void testViewBillsThrowsMemberNotFoundException() throws IllegalAccessException, InvocationTargetException {

		when(authClient.authorizeTheRequest(authToken)).thenReturn(true);

		Premium premium = new Premium();
		when(premiumRepo.findByMemberIdAndPolicyId(memberId, policyId)).thenReturn(premium);
		when(memberRepo.findById(memberId)).thenReturn(Optional.empty());

		assertThrows(MemberNotFoundException.class, () -> {
			service.viewBills(memberId, policyId, authToken);
		});
	}
	
	/**
	 * A method to test view_bills throws InternalServerError
	 */
	@Test
	void testViewBillsThrowsInternalServerError() {

		when(authClient.authorizeTheRequest(authToken)).thenReturn(true);

		assertThrows(TokenExpireException.class, () -> {
			service.viewBills(memberId, policyId, "wrongToken");
		});
	}
	
	/**
	 * A method to test get_claim_status method
	 * @throws MissingRequestHeaderException
	 * @throws ClaimNotFoundException
	 * @throws TokenExpireException
	 * @throws MemberNotFoundException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@Test
	void testGetClaimStatus() throws MissingRequestHeaderException, ClaimNotFoundException, TokenExpireException,
			MemberNotFoundException, IllegalAccessException, InvocationTargetException {

		ClaimStatusOutput claimStatusOutput = ClaimStatusOutput.builder().build();
		when(claimClient.getClaimStatus(claimId, authToken)).thenReturn(claimStatusOutput);

		Member member = new Member();
		MemberClaim expected = MemberClaim.builder().member(member).build();
		when(memberRepo.findById(memberId)).thenReturn(Optional.of(member));

		MemberClaim actual = service.getClaimStatus(claimId, memberId, authToken);

		assertThat(actual).isInstanceOf(MemberClaim.class);
		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}
	
	/**
	 * A method to test get_claim_status throws ClaimNotFoundException
	 * @throws MissingRequestHeaderException
	 * @throws ClaimNotFoundException
	 * @throws TokenExpireException
	 * @throws MemberNotFoundException
	 */
	@Test
	void testGetClaimStatusClaimNotFoundException() throws MissingRequestHeaderException, ClaimNotFoundException,
			TokenExpireException, MemberNotFoundException {

		when(claimClient.getClaimStatus(claimId, authToken)).thenReturn(null);

		assertThrows(ClaimNotFoundException.class, () -> {
			service.getClaimStatus(claimId, memberId, authToken);
		});
	}
	
	/**
	 * A method to test get_claim_status throws MemberNotFoundException
	 * @throws MissingRequestHeaderException
	 * @throws ClaimNotFoundException
	 * @throws TokenExpireException
	 * @throws MemberNotFoundException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@Test
	void testGetClaimStatusMemberNotFoundException() throws MissingRequestHeaderException, ClaimNotFoundException,
			TokenExpireException, MemberNotFoundException, IllegalAccessException, InvocationTargetException {

		ClaimStatusOutput claimStatusOutput = ClaimStatusOutput.builder().build();
		when(claimClient.getClaimStatus(claimId, authToken)).thenReturn(claimStatusOutput);

		when(memberRepo.findById(memberId)).thenReturn(Optional.empty());

		assertThrows(MemberNotFoundException.class, () -> {
			service.getClaimStatus(claimId, memberId, authToken);
		});
	}
	
	/**
	 * A method to test get_claim_status throws TokenExpireException
	 * @throws MissingRequestHeaderException
	 * @throws ClaimNotFoundException
	 * @throws TokenExpireException
	 * @throws MemberNotFoundException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@Test
	void testGetClaimStatusTokenExpireException() throws MissingRequestHeaderException, ClaimNotFoundException,
			TokenExpireException, MemberNotFoundException, IllegalAccessException, InvocationTargetException {

		when(claimClient.getClaimStatus(claimId, authToken)).thenThrow(TokenExpireException.class);

		assertThrows(TokenExpireException.class, () -> {
			service.getClaimStatus(claimId, memberId, authToken);
		});
	}
	
	/**
	 * A method to test submit_claim method
	 * @throws PolicyNotFoundException
	 * @throws TokenExpireException
	 */
	@Test
	void testSubmitClaim() throws PolicyNotFoundException, TokenExpireException {

		Claim expected = Claim.builder().build();
		ClaimInput claimInput = ClaimInput.builder().build();

		when(claimClient.submitClaim(claimInput, authToken)).thenReturn(expected);
		Claim actual = service.submitClaim(claimInput, authToken);

		assertThat(actual).isInstanceOf(Claim.class);
		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}
	
	/**
	 * A method to test submit_claim throws PolicyNotFoundException
	 * @throws PolicyNotFoundException
	 * @throws TokenExpireException
	 */
	@Test
	void testSubmitClaimThrowsPolicyNotFoundException() throws PolicyNotFoundException, TokenExpireException {

		ClaimInput claimInput = ClaimInput.builder().build();
		when(claimClient.submitClaim(claimInput, authToken)).thenThrow(PolicyNotFoundException.class);

		assertThrows(PolicyNotFoundException.class, () -> {
			service.submitClaim(claimInput, authToken);
		});

	}
	
	/**
	 * A method to test submit_claim throws TokenExpireException
	 * @throws PolicyNotFoundException
	 * @throws TokenExpireException
	 */
	@Test
	void testSubmitClaimThrowsTokenExpireException() throws PolicyNotFoundException, TokenExpireException {

		ClaimInput claimInput = ClaimInput.builder().build();
		when(claimClient.submitClaim(claimInput, authToken)).thenThrow(TokenExpireException.class);

		assertThrows(TokenExpireException.class, () -> {
			service.submitClaim(claimInput, authToken);
		});

	}

}
