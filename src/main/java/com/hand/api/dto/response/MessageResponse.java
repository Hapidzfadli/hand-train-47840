package com.hand.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("Message Response")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    @ApiModelProperty("Success status")
    private Boolean success;

    @ApiModelProperty("Response Message")
    private String message;

    @ApiModelProperty("Response Body")
    private Object body;

    public static MessageResponse success(String message) {
        return MessageResponse.builder()
                .success(true)
                .message(message)
                .build();
    }

    public static MessageResponse success(String message, Object body) {
        return MessageResponse.builder()
                .success(true)
                .message(message)
                .body(body)
                .build();
    }

    public static MessageResponse error(String message) {
        return MessageResponse.builder()
                .success(false)
                .message(message)
                .build();
    }
}