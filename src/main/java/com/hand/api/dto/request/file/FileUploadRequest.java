package com.hand.api.dto.request.file;

import com.hand.api.dto.request.FileRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

@Data
@EqualsAndHashCode(callSuper = true)
public class FileUploadRequest extends FileRequest {
    private String directory;

    @NotNull(message = "File cannot be empty")
    private MultipartFile file;
}