package com.example.rqs.api.item.randomquiz;

import com.example.rqs.api.cache.quiz.QuizCache;
import com.example.rqs.api.cache.quiz.QuizCacheService;
import com.example.rqs.api.jwt.MemberDetails;
import com.example.rqs.core.item.service.ItemReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quiz")
@RequiredArgsConstructor
public class RandomQuizController {
    private final QuizCacheService quizCacheService;
    private final ItemReadService itemReadService;

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


    // get random quiz
    @GetMapping("/pick/{spaceId}")
    // TODO: Object -> RandomItem
    public ResponseEntity<Object> pickRandomQuiz(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable("spaceId") Long spaceId
    ) {
        Long memberId = memberDetails.getMember().getMemberId();
        boolean inProgress = quizCacheService.inProgress(spaceId, memberId);
        if (!inProgress) {
            /** TODO: itemReadService.getItemIds(spaceId);
             * Remove itemRandomService
             */
            quizCacheService.start(spaceId, memberId, List.of());
        }
        Long randomQuizId = quizCacheService.pickRandomQuizId(spaceId, memberId);
        // TODO: null -> RandomItem
        return ResponseEntity.ok(null);
    }
}
