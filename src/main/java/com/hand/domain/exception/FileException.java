package com.hand.domain.exception;

import lombok.Getter;

@Getter
public class FileException extends RuntimeException {
    private final String code;
    private final String message;

    public FileException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public FileException(String message) {
        this("FILE_ERROR", message);
    }
}
