package com.bergamota.jasperreport.resources.exception;

import java.util.Objects;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.bergamota.jasperreport.exceptions.base.BaseHttpException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	  @Value("${reflectoring.trace:true}")
	  private boolean printStackTrace;
	  public static final String TRACE = "trace";
	  
	  
	  @ExceptionHandler(BaseHttpException.class)
	  public ResponseEntity<ErrorResponse> badRequest(BaseHttpException ex, WebRequest req) {		  
		  return buildErrorResponse(ex, HttpStatus.valueOf(ex.getStatus()), req);
	  }
	  
	  /*@ExceptionHandler(EntityNotFoundException.class)
	  public ResponseEntity<ErrorResponse> notFound(EntityNotFoundException ex, WebRequest req) {		  
		  return buildErrorResponse(ex, HttpStatus.NOT_FOUND, req);
	  }
	  
	  
	  @ExceptionHandler(FileNotFoundException.class)
	  public ResponseEntity<ErrorResponse> fileNotFound(FileNotFoundException ex, WebRequest req) {		  
		  return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, req);
	  }
	  
	  @ExceptionHandler(JasperReportGeneratorException.class)
	  public ResponseEntity<ErrorResponse> jasperReportGeneratorError(JasperReportGeneratorException ex, WebRequest req) {		  
		  return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, req);
	  }
	  */
	  
	private ResponseEntity<ErrorResponse> buildErrorResponse(
			Exception exception,
			HttpStatus httpStatus,
			WebRequest request
			) {
		return buildErrorResponse(
				exception, 
				exception.getMessage(), 
				httpStatus, 
				request);
	}

	private ResponseEntity<ErrorResponse> buildErrorResponse(
			Exception exception,
			String message,
			HttpStatus httpStatus,
			WebRequest request
			) {
		ErrorResponse errorResponse = new ErrorResponse(
				httpStatus.value(), 
				exception.getMessage()				
				);

		if(printStackTrace && isTraceOn(request)){
			errorResponse.setDetailedMessage(ExceptionUtils.getStackTrace(exception));
		}
		return ResponseEntity.status(httpStatus).body(errorResponse);
	}

	private boolean isTraceOn(WebRequest request) {
		String [] value = request.getParameterValues(TRACE);
		return Objects.nonNull(value)
				&& value.length > 0
				&& value[0].contentEquals("true");
						
	}
}
