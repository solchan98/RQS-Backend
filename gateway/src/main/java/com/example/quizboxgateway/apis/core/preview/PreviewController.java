package com.example.quizboxgateway.apis.core.preview;

import com.example.quizboxgateway.apis.core.CoreApiResponse;
import com.example.quizboxgateway.auth.auth.AccessUser;
import com.example.quizboxgateway.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/core/preview")
@RequiredArgsConstructor
public class PreviewController {

    private final PreviewApi previewApi;

    @GetMapping
    public ResponseEntity<CommonResponse<List<QueryPreviewResponse>>> getAllPreview(AccessUser accessUser) {
        CoreApiResponse<List<QueryPreviewResponse>> result =
                previewApi.queryPreviewResponse(accessUser.getId());

        return ResponseEntity.ok(
                new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "프리뷰 전체 조회 성공",
                        result.getData()
                )
        );
    }

    @PostMapping
    public ResponseEntity<CommonResponse<QueryPreviewResponse>> savePreview(
            AccessUser accessUser,
            @RequestBody CommandPreviewRequest request
    ) {
        CoreApiResponse<QueryPreviewResponse> result =
                previewApi.commandSavePreview(accessUser.getId(), request);

        return ResponseEntity.ok(
                new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "프리뷰 퀴즈팩 저장 성공",
                        result.getData()
                )
        );
    }

    @PostMapping("publish/{preview_quiz_pack_id}")
    public ResponseEntity<CommonResponse<Long>> publishPreview(
            AccessUser accessUser,
            @PathVariable("preview_quiz_pack_id") Long previewQuizPackId
    ) {
        CoreApiResponse<Long> result = previewApi.commandPublishPreview(accessUser.getId()
                , previewQuizPackId);

        return ResponseEntity.ok(
                new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "퀴즈팩 발행 성공",
                        result.getData()
                )
        );
    }
}
