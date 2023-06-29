package com.bergamota.jasperreports.application.exception;

import com.jeterson.areaclientewinthor.common.application.GlobalExceptionHandler;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Import({ConnectionApplicationExceptionHandler.class,CategoryApplicationExceptionHandler.class,ReportApplicationExceptionHandler.class})
@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class ApplicationExceptionHandler extends GlobalExceptionHandler {
}