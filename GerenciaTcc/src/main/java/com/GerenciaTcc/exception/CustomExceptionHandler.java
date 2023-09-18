package com.GerenciaTcc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<MessageException> handleBadRequestException(BadRequestException e, HttpServletRequest req) {

        MessageException me = MessageException.builder()
                .message(e.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .timeStamp(LocalDateTime.now())
                .uri(req.getRequestURI())
                .build();

        return ResponseEntity.badRequest().body(me);
    }
}