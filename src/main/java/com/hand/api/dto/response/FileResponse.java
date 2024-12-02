package com.hand.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileResponse<T> {
    private String status;
    private String message;
    private T data;

    public static <T> FileResponse<T> success(String message, T data) {
        return FileResponse.<T>builder()
                .status("SUCCESS")
                .message(message)
                .data(data)
                .build();
    }

    public static <T> FileResponse<T> success(String message) {
        return success(message, null);
    }

    public static <T> FileResponse<T> error(String message) {
        return FileResponse.<T>builder()
                .status("ERROR")
                .message(message)
                .build();
    }
}
