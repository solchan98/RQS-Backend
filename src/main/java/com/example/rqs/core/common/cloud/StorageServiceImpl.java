package com.example.rqs.core.common.cloud;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class StorageServiceImpl implements StorageService {

    @Value("${cloud.storage.key}")
    private String KEY_PATH;
    @Value("${cloud.storage.bucket}")
    private String BUCKET;
    private static final String BASE_URI = "https://storage.googleapis.com/";

    @Override
    public String upload(MultipartFile file, String path) throws IOException {
        String filename = makeFilename(file);
        InputStream key = ResourceUtils.getURL(KEY_PATH).openStream();
        Storage storage = StorageOptions.newBuilder()
                .setProjectId(BUCKET)
                .setCredentials(GoogleCredentials.fromStream(key))
                .build()
                .getService();
        BlobInfo blobInfo = storage.create(
                BlobInfo.newBuilder(BUCKET, path + "/" + filename)
                        .build(),
                filename.getBytes());
        return BASE_URI + blobInfo.getBucket() + blobInfo.getName();
    }

    private String makeFilename(MultipartFile file) {
        int dotIndex = Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".");
        String filename = file.getOriginalFilename().substring(0, dotIndex);
        String extension = file.getOriginalFilename().substring(dotIndex);
        return filename + "_" + LocalDateTime.now() + extension;
    }
}
