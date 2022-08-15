package com.cts.membermicroservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * A proxy class for calling cms-auth-service
 * @author SuhruthY
 */
@FeignClient(url = "${auth.url}", name = "cms-auth-service")
public interface AuthClient {
	/**
	 * This method authorizes the user by provided token
	 * @param requestTokenHeader - jwt to verify
	 * @return true if jwt is valid or else false
	 */
	@PostMapping("/authorize")
	public boolean authorizeTheRequest(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader);

}
