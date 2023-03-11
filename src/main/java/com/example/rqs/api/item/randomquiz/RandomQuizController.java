package com.example.rqs.api.item.randomquiz;

import com.example.rqs.api.cache.quiz.QuizCache;
import com.example.rqs.api.cache.quiz.QuizCacheService;
import com.example.rqs.api.jwt.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/quiz")
@RequiredArgsConstructor
public class RandomQuizController {
    private final QuizCacheService quizCacheService;

    // was started QuizGame
    @GetMapping("/progress/{spaceId}")
    public ResponseEntity<InProgressResponse> progress(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable("spaceId") Long spaceId
    ) {
        Long memberId = memberDetails.getMember().getMemberId();
        boolean inProgress = quizCacheService.inProgress(spaceId, memberId);
        if (inProgress) {
            QuizCache quizCache = quizCacheService.status(spaceId, memberId);
            return ResponseEntity.ok(InProgressResponse.from(quizCache));
        }
        return ResponseEntity.ok(InProgressResponse.of(false));
    }
}
