package com.example.rqs.core.common.cloud;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    String upload(MultipartFile file, String path) throws IOException;
}
