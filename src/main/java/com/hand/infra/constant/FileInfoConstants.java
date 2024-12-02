package com.hand.infra.constant;

public class FileInfoConstants {

    private FileInfoConstants() {
    }
    public static class FileConfig {
        public static final String STORAGE_CODE = "MINIO_21995";
    }


    public static class FileType {
        public static final String TEXT_PLAIN = "text/plain";
        public static final String PDF_DOCUMENT = "application/pdf";
        public static final String JPEG_IMAGE = "image/jpeg";
        public static final String PNG_IMAGE = "image/png";
    }

    public static class SuccessMessage {
        public static final String FILE_UPLOADED = "File uploaded successfully";
        public static final String FILE_DOWNLOADED = "File downloaded successfully";
        public static final String FILE_DELETED = "File(s) deleted successfully";
        public static final String FILES_FOUND = "Files retrieved successfully";
    }

    public static class ErrorMessage {
        public static final String FILE_INVALID_EXTENSION = "File extension not supported. Only .txt files are allowed";
        public static final String FILE_SIZE_EXCEEDED = "File size exceeds maximum limit of 5MB";
        public static final String FILE_UPLOAD_FAILED = "Failed to upload file";
        public static final String FILE_DOWNLOAD_FAILED = "Failed to download file";
        public static final String FILE_DELETE_FAILED = "Failed to delete file";
        public static final String FILE_SEARCH_FAILED = "Failed to search files";
        public static final String INVALID_CONTENT_TYPE = "Invalid content type. Only text/plain is allowed";
        public static final String UNEXPECTED_ERROR = "An unexpected error occurred";
        public static final String SEARCH_RESULT_EMPTY = "Search result empty file not found";
    }

    public static class ValidationRules {
        public static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
        public static final String ALLOWED_EXTENSION = "txt";
    }


    public static class ErrorCode {
        public static final String UPLOAD_FAILED = "UPLOAD_FAILED";
        public static final String DOWNLOAD_FAILED = "DOWNLOAD_FAILED";
        public static final String DELETE_FAILED = "DELETE_FAILED";
        public static final String SEARCH_FAILED = "SEARCH_FAILED";
        public static final String INVALID_EXTENSION = "INVALID_EXTENSION";
        public static final String INVALID_CONTENT_TYPE = "INVALID_CONTENT_TYPE";
        public static final String FILE_TOO_LARGE = "FILE_TOO_LARGE";
        public static final String SEARCH_RESULT_EMPTY = "SEARCH_RESULT_EMPTY";
    }
}