package com.GerenciaTcc.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class MessageException {
    private String message;
    private Integer httpStatus;
    private LocalDateTime timeStamp;
    private String uri;
}