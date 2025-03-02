package org.example.quizbox.preview.presentation;

import lombok.RequiredArgsConstructor;
import org.example.quizbox.common.CommonResponse;
import org.example.quizbox.preview.application.PreviewQuizPackPublishService;
import org.example.quizbox.preview.application.PreviewQuizPackReadAllService;
import org.example.quizbox.preview.application.PreviewQuizPackSaveService;
import org.example.quizbox.preview.domain.PreviewQuizPack;
import org.example.quizbox.preview.domain.PreviewQuizPackType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("preview")
@RequiredArgsConstructor
public class PreviewController {

    private final PreviewQuizPackReadAllService previewQuizPackReadAllService;

    private final PreviewQuizPackSaveService previewQuizPackSaveService;
    private final PreviewQuizPackPublishService previewQuizPackPublishService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<PreviewQuizPack>>> queryAll(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestParam(value = "preview_type", required = false) PreviewQuizPackType previewQuizPackType
    ) {
        return ResponseEntity.ok(
                new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "프리뷰 퀴즈팩 전체 조회 성공",
                        previewQuizPackReadAllService.query(userId, previewQuizPackType)
                )
        );
    }

    @PostMapping
    public ResponseEntity<CommonResponse<PreviewQuizPack>> commandSave(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestBody PreviewQuizPack previewQuizPack
    ) {
        return ResponseEntity.ok(
                new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "프리뷰 퀴즈팩 저장 성공",
                        previewQuizPackSaveService.command(userId, previewQuizPack)
                )
        );
    }

    @PostMapping("publish/{preview_quiz_pack_id}")
    public ResponseEntity<CommonResponse<Long>> commandPublish(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable("preview_quiz_pack_id") Long previewQuizPackId
    ) {
        return ResponseEntity.ok(
                new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "퀴즈팩 발행 성공",
                        previewQuizPackPublishService.command(userId, previewQuizPackId).getId()
                )
        );
    }
}
