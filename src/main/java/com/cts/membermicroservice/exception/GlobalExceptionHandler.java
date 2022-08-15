package com.cts.membermicroservice.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cts.membermicroservice.pojo.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * A exception handler to handle all known exceptions
 * @author SuhruthY
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	/**
	 * This method handles all other unknown errors and shows a internal_server_error
	 * @param ex - exception raised
	 * @param request - generic web request
	 * @return error response as json
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
		ErrorResponse response = ErrorResponse.builder().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.statusDate(new Date()).statusMsg(ex.getMessage()).build();
		if (log.isErrorEnabled()) {
			log.error(ex.getMessage());
		}
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * This method handles all other unknown errors and shows a unauthorized_error
	 * @param ex - exception raised(TokenExpireException)
	 * @param request - generic web request
	 * @return error response as json - status code 401
	 */
	@ExceptionHandler(TokenExpireException.class)
	public ResponseEntity<Object> handleTokenExpireException(TokenExpireException ex, WebRequest request) {
		ErrorResponse response = ErrorResponse.builder().statusCode(HttpStatus.UNAUTHORIZED.value())
				.statusDate(new Date()).statusMsg(ex.getMessage()).build();
		if (log.isErrorEnabled()) {
			log.error(ex.getMessage());
		}
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}
	
	/**
	 * This method handles all other unknown errors and shows a not_found_error
	 * @param ex - exception raised
	 * @param request - generic web request
	 * @return error response as json - status code 404
	 */
	@ExceptionHandler({ ClaimNotFoundException.class, MemberNotFoundException.class, PolicyNotFoundException.class,
			PremiumNotFoundException.class })
	public ResponseEntity<Object> handleNotFoundException(Exception ex, WebRequest request) {
		ErrorResponse response = ErrorResponse.builder().statusCode(HttpStatus.NOT_FOUND.value()).statusDate(new Date())
				.statusMsg(ex.getMessage()).build();
		if (log.isErrorEnabled()) {
			log.error(ex.getMessage());
		}
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

}