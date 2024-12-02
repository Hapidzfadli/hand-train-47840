package com.hand.domain.exception;

import com.hand.api.dto.response.FileResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FileException.class)
    public ResponseEntity<FileResponse<Void>> handleFileException(FileException ex) {
        log.error("File operation error: {} - {}", ex.getCode(), ex.getMessage());
        return ResponseEntity.badRequest()
                .body(FileResponse.<Void>builder()
                        .status("ERROR")
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<FileResponse<Void>> handleMaxSizeException(MaxUploadSizeExceededException ex) {
        return ResponseEntity.badRequest()
                .body(FileResponse.<Void>builder()
                        .status("ERROR")
                        .message("File size exceeds the maximum allowed size")
                        .build());
    }
}