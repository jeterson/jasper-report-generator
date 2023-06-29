package com.bergamota.jasperreports.application.exception;

import com.bergamota.jasperreports.domain.core.exceptions.CategoryDomainException;
import com.bergamota.jasperreports.domain.core.exceptions.CategoryNotFoundException;
import com.bergamota.jasperreports.domain.core.exceptions.ReportDomainException;
import com.bergamota.jasperreports.domain.core.exceptions.ReportNotFoundException;
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
public class ReportApplicationExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = {ReportNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleException(ReportNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return ErrorDTO.builder()
                .code(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(exception.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(value = {ReportDomainException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleException(ReportDomainException exception) {
        log.error(exception.getMessage(), exception);
        return ErrorDTO.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(exception.getMessage())
                .build();
    }
}
