package com.example.rqs.api.cloud;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Getter
public class UploadDto {
    private MultipartFile file;
}
