package com.hand.api.dto.request.file;

import com.hand.api.dto.request.FileRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class FileDeleteRequest extends FileRequest {
    @NotEmpty(message = "URLs list cannot be empty")
    private List<String> urls;
}