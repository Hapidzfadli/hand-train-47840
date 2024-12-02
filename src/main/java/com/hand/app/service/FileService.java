package com.hand.app.service;

import com.hand.api.dto.request.file.FileDeleteRequest;
import com.hand.api.dto.request.file.FileDownloadRequest;
import com.hand.api.dto.request.file.FileSearchRequest;
import com.hand.api.dto.request.file.FileUploadRequest;
import org.hzero.boot.file.dto.FileDTO;
import java.io.InputStream;
import java.util.List;


public interface FileService {
    String uploadFile(FileUploadRequest request, Long organizationId);
    InputStream downloadFile(FileDownloadRequest request, Long organizationId);
    void deleteFile(FileDeleteRequest request, Long organizationId);
    List<FileDTO> getFiles(FileSearchRequest request, Long organizationId);
}
