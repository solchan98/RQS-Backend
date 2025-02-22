package com.example.quizboxgateway.apis.core.quizpacks;

import com.example.quizboxgateway.apis.core.CoreApiResponse;
import com.example.quizboxgateway.auth.auth.AccessUser;
import com.example.quizboxgateway.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/core/quiz-packs")
@RequiredArgsConstructor
public class QuizPackController {

    private final QuizPacksApi quizPacksApi;

    @GetMapping
    public ResponseEntity<CommonResponse<List<QueryQuizPacksResponse>>> getQuizPacks(
            AccessUser accessUser,
            @RequestParam(value = "lastId", required = false) Long lastId,
            @RequestParam(value = "chunk") int chunk,
            @RequestParam(value = "searchType") String searchType
    ) {
        CoreApiResponse<List<QueryQuizPacksResponse>> result =
                quizPacksApi.queryQuizPacks(accessUser.getId(), lastId, chunk, searchType);

        return ResponseEntity.ok(
                new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "퀴즈팩 조회 성공",
                        result.getData()
                )
        );
    }

    @GetMapping("{quiz-pack-id}")
    public ResponseEntity<CommonResponse<QueryQuizPackDetailResponse>> getQuizPackDetails(
            AccessUser accessUser,
            @PathVariable("quiz-pack-id") Long quizPackId
    ) {
        CoreApiResponse<QueryQuizPackDetailResponse> result =
                quizPacksApi.queryQuizPackDetails(accessUser.getId(), quizPackId);

        return ResponseEntity.ok(
                new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "퀴즈팩 디테일 조회 성공",
                        result.getData()
                )
        );
    }

    @GetMapping("{quiz-pack-id}/quizzes")
    public ResponseEntity<CommonResponse<List<QueryQuizzesResponse>>> getQuizzesResponse(
            AccessUser accessUser,
            @PathVariable("quiz-pack-id") Long quizPackId
    ) {
        CoreApiResponse<List<QueryQuizzesResponse>> result = quizPacksApi.queryQuizzes(accessUser.getId(), quizPackId);

        return ResponseEntity.ok(
                new CommonResponse<>(
                        HttpStatus.OK.name(),
                        "퀴즈팩 퀴즈 조회 성공",
                        result.getData()
                )
        );
    }
}
