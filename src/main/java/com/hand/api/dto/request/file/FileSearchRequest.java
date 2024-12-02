package com.hand.api.dto.request.file;

import com.hand.api.dto.request.FileRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
public class FileSearchRequest extends FileRequest {
//    @NotBlank(message = "Attachment UUID cannot be empty")
//    private String attachmentUUID;
}