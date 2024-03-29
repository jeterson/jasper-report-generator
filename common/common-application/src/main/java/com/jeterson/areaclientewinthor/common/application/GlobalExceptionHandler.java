package com.jeterson.areaclientewinthor.common.application;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public abstract class GlobalExceptionHandler {


    @ResponseBody
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ErrorDTO.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message("Unexpected error!")
                .build();
    }

    @ResponseBody
    @ExceptionHandler(value = {com.bergamota.jasperreports.common.domain.exception.ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleException(com.bergamota.jasperreports.common.domain.exception.ConstraintViolationException exception) {
        log.error(exception.getMessage(), exception);
        log.error("Internal Error {}-{}", exception.getInternalCode().code(), exception.getInternalCode().defaultMessage());
        return ErrorDTO.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(exception.getMessage())
                .internalError(new ErrorDTO(exception.getInternalCode().code(), exception.getInternalCode().defaultMessage()))
                .build();
    }

    @ResponseBody
    @ExceptionHandler(value = {IllegalArgumentException.class})
    //@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleException(IllegalArgumentException exception) {
        log.error(exception.getMessage(), exception);
        return ErrorDTO.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(exception.getMessage())
                .build();
    }
    @ResponseBody
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleException(MethodArgumentNotValidException exception) {
        var bindingResult = exception.getBindingResult();
        var errors = bindingResult.getFieldErrors();
        var errorsResponse = processFieldErrors(errors);

        return ErrorDTO.builder()
                .details(errorsResponse)
                .message("Validation Error")
                .code("Validation")
                .build();
    }


    private List<ErrorDTO> processFieldErrors(List<FieldError> fieldErrors) {
        List<ErrorDTO> errors = new ArrayList<>();

        for (FieldError fieldError: fieldErrors) {
            String localizedErrorMessage = fieldError.getDefaultMessage();
            errors.add(ErrorDTO.builder().code(fieldError.getField()).message(localizedErrorMessage).build());
        }

        return errors;
    }

    @ResponseBody
    @ExceptionHandler(value = {ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleException(ValidationException validationException) {
        ErrorDTO errorDTO;
        if (validationException instanceof ConstraintViolationException) {
            var violations = extractViolationsFromException((ConstraintViolationException) validationException);

            log.error(violations.stream().map(v -> v.getCode() + "="+v.getMessage()).collect(Collectors.joining("--")), validationException);
            errorDTO = ErrorDTO.builder()
                    .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                    .message("Validation Error")
                    .details(violations)
                    .build();
        } else {
            String exceptionMessage = validationException.getMessage();
            log.error(exceptionMessage, validationException);
            errorDTO = ErrorDTO.builder()
                    .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                    .message(exceptionMessage)
                    .build();
        }
        return errorDTO;
    }

    private List<ErrorDTO> extractViolationsFromException(ConstraintViolationException validationException) {

        return validationException.getConstraintViolations()
                .stream()
                .map(v -> ErrorDTO.builder().code(v.getPropertyPath().toString()).message(v.getMessage()).build())
                .collect(Collectors.toList());
    }
}
