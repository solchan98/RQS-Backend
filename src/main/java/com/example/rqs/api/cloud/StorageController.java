package com.example.rqs.api.cloud;

import com.example.rqs.api.exception.Message;
import com.example.rqs.api.jwt.MemberDetails;
import com.example.rqs.core.common.cloud.StorageService;
import com.example.rqs.core.common.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/storage")
public class StorageController {

    private final StorageService storageService;

    private StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/image/{target}")
    public ResponseEntity<Message> uploadFile(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable("target") String target,
            UploadDto uploadDto
    ) {
        boolean imageIsNull = Objects.isNull(uploadDto.getFile());
        if (imageIsNull) throw new BadRequestException();
        try {
            storageService.upload(
                    uploadDto.getFile(),
                    target + "/" + memberDetails.getUsername()
            );
        } catch (IOException e) {
            return ResponseEntity.ok(new Message(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        }
        return ResponseEntity.ok(new Message("파일 업로드 성공", HttpStatus.CREATED));
    }

}
