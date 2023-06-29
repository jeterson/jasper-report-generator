package com.bergamota.jasperreports.application.exception;

import com.bergamota.jasperreports.domain.core.exceptions.ConnectionConfigDomainException;
import com.bergamota.jasperreports.domain.core.exceptions.ConnectionConfigNotFoundException;
import com.jeterson.areaclientewinthor.common.application.ErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ConnectionApplicationExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = {ConnectionConfigNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleException(ConnectionConfigNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return ErrorDTO.builder()
                .code(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(exception.getMessage())
                .build();
    }
    @ResponseBody
    @ExceptionHandler(value = {ConnectionConfigDomainException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleException(ConnectionConfigDomainException exception) {
        log.error(exception.getMessage(), exception);
        return ErrorDTO.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(exception.getMessage())
                .build();
    }
}
