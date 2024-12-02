package com.hand.api.controller.v1;

import com.hand.api.dto.request.file.*;
import com.hand.api.dto.response.FileResponse;
import com.hand.infra.constant.FileInfoConstants;
import com.hand.app.service.FileService;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.hzero.boot.file.dto.FileDTO;
import org.hzero.core.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@RestController
@Api(tags = "File Management")
@RequestMapping("/v1/{organizationId}/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    @Permission(level = ResourceLevel.SITE)
    @ApiOperation("Upload File")
    public ResponseEntity<Map<String, String>> uploadFile(
            @Valid FileUploadRequest request, @PathVariable Long organizationId) {

        String data = fileService.uploadFile(request, organizationId);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("data", data);

        return Results.success(resultMap);
    }

    @PostMapping("/download")
    @Permission(level = ResourceLevel.SITE)
    @ApiOperation("Download File")
    public ResponseEntity<InputStreamResource> downloadFile(
            @Valid @RequestBody FileDownloadRequest request, @PathVariable Long organizationId) {
        log.info("Received file download request for URL: {}", request.getUrl());

        InputStream inputStream = fileService.downloadFile(request, organizationId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDispositionFormData("attachment",
                FilenameUtils.getName(request.getUrl()));

        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(inputStream));
    }

    @DeleteMapping
    @Permission(level = ResourceLevel.SITE)
    @ApiOperation("Delete File")
    public ResponseEntity<FileResponse<Void>> deleteFile(
            @Valid @RequestBody FileDeleteRequest request, @PathVariable Long organizationId) {
        log.info("Received file delete request for {} URLs", request.getUrls().size());

        fileService.deleteFile(request, organizationId);

        return ResponseEntity.ok(
                FileResponse.success(FileInfoConstants.SuccessMessage.FILE_DELETED)
        );
    }

    @PostMapping("/search")
    @Permission(level = ResourceLevel.SITE)
    @ApiOperation("Search Files")
    public ResponseEntity<FileResponse<List<FileDTO>>> getFiles(
            @Valid @RequestBody FileSearchRequest request, @PathVariable Long organizationId) {
        List<FileDTO> files = fileService.getFiles(request, organizationId);

        return ResponseEntity.ok(
                FileResponse.success(
                        FileInfoConstants.SuccessMessage.FILES_FOUND,
                        files
                )
        );
    }
}