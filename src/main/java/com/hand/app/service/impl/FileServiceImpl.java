package com.hand.app.service.impl;

import com.hand.api.dto.request.file.FileDeleteRequest;
import com.hand.api.dto.request.file.FileDownloadRequest;
import com.hand.api.dto.request.file.FileSearchRequest;
import com.hand.api.dto.request.file.FileUploadRequest;
import com.hand.infra.constant.FileInfoConstants;
import com.hand.domain.exception.FileException;
import com.hand.app.service.FileService;
import io.choerodon.core.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.hzero.boot.file.FileClient;
import org.hzero.boot.file.dto.FileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import static com.hand.infra.constant.FileInfoConstants.FileConfig.STORAGE_CODE;
import static com.hand.infra.constant.FileInfoConstants.FileType.TEXT_PLAIN;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileClient fileClient;

    @Override
    public String uploadFile(FileUploadRequest request, Long organizationId) {
        log.debug("Starting file upload process for file: {}",
                request.getFile().getOriginalFilename());

        log.info("Uploading file: {}", request.getFile());

        validateFileContent(request.getFile());

        try {
            String result = fileClient.uploadFile(
                    organizationId,
                    request.getBucketName(),
                    request.getDirectory(),
                    request.getFile().getOriginalFilename(),
                    TEXT_PLAIN,
                    STORAGE_CODE,
                    request.getFile().getBytes()
            );

            log.info("File uploaded successfully: {}", request.getFile().getOriginalFilename());
            return result;

        } catch (Exception e) {
            log.error("Error uploading file: {}", e.getMessage(), e);
            throw new FileException(
                    FileInfoConstants.ErrorCode.UPLOAD_FAILED,
                    FileInfoConstants.ErrorMessage.FILE_UPLOAD_FAILED
            );
        }
    }

    @Override
    public InputStream downloadFile(FileDownloadRequest request, Long organizationId) {
        log.debug("Starting file download process for URL: {}", request.getUrl());

        try {
            InputStream result = fileClient.downloadFile(
                    organizationId,
                    request.getBucketName(),
                    request.getUrl()
            );

            log.info("File downloaded successfully from URL: {}", request.getUrl());
            return result;

        } catch (Exception e) {
            log.error("Error downloading file: {}", e.getMessage(), e);
            throw new FileException(
                    FileInfoConstants.ErrorCode.DOWNLOAD_FAILED,
                    FileInfoConstants.ErrorMessage.FILE_DOWNLOAD_FAILED
            );
        }
    }

    @Override
    public void deleteFile(FileDeleteRequest request, Long organizationId) {
        log.debug("Starting file deletion process for {} URLs", request.getUrls().size());

        try {
            fileClient.deleteFileByUrl(
                    organizationId,
                    request.getBucketName(),
                    request.getUrls()
            );

            log.info("Files deleted successfully. Count: {}", request.getUrls().size());

        } catch (Exception e) {
            log.error("Error deleting files: {}", e.getMessage(), e);
            throw new FileException(
                    FileInfoConstants.ErrorCode.DELETE_FAILED,
                    FileInfoConstants.ErrorMessage.FILE_DELETE_FAILED
            );
        }
    }

    @Override
    public List<FileDTO> getFiles(FileSearchRequest request, Long organizationId) {
        log.debug("Starting file search fo Organization Id: {}", organizationId);

        try {
            String uuid = fileClient.getAttachmentUUID(organizationId);
            log.info("uuid: {}", uuid);
            List<FileDTO> results = fileClient.getAttachmentFiles(
                    organizationId,
                    request.getBucketName(),
                    "$"
            );

            log.info("Files found successfully. Count: {}", results.size());
            return results;

        } catch (Exception e) {
            log.error("Error searching files: {}", e.getMessage(), e);
            throw new FileException(
                    FileInfoConstants.ErrorCode.SEARCH_FAILED,
                    FileInfoConstants.ErrorMessage.FILE_SEARCH_FAILED
            );
        }
    }

    private void validateFileContent(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String extension = FilenameUtils.getExtension(originalFilename);

        log.debug("Validating file: {}, size: {}, type: {}",
                originalFilename, file.getSize(), file.getContentType());

        if (!FileInfoConstants.ValidationRules.ALLOWED_EXTENSION.equalsIgnoreCase(extension)) {
            throw new CommonException(
                    FileInfoConstants.ErrorMessage.FILE_INVALID_EXTENSION,
                    FileInfoConstants.ErrorCode.INVALID_EXTENSION
            );
        }

        if (file.getSize() > FileInfoConstants.ValidationRules.MAX_FILE_SIZE) {
            throw new CommonException(FileInfoConstants.ErrorMessage.FILE_SIZE_EXCEEDED);
        }

        if (!TEXT_PLAIN.equals(file.getContentType())) {
            throw new CommonException(FileInfoConstants.ErrorMessage.INVALID_CONTENT_TYPE);
        }
    }
}