package com.hand.api.dto.request;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public abstract class FileRequest {

    @NotNull(message = "Bucket name cannot be empty")
    private String bucketName;
}