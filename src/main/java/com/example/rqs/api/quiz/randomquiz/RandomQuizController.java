package com.example.rqs.api.quiz.randomquiz;

import com.example.rqs.api.cache.quiz.QuizCache;
import com.example.rqs.api.cache.quiz.QuizCacheService;
import com.example.rqs.api.jwt.MemberDetails;
import com.example.rqs.core.quiz.service.QuizReadService;
import com.example.rqs.core.quiz.service.dtos.QuizResponse;
import com.example.rqs.core.quiz.service.dtos.ReadQuiz;
import com.example.rqs.core.space.service.SpaceReadService;
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
    private final QuizReadService quizReadService;
    private final SpaceReadService spaceReadService;

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

    @GetMapping("/random/{spaceId}/{type}")
    public ResponseEntity<QuizResponse> pickRandomQuiz(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable("spaceId") Long spaceId,
            @PathVariable("type") String type
    ) {
        Long memberId = memberDetails.getMember().getMemberId();
        spaceReadService.checkReadableQuiz(memberId, spaceId);

        boolean inProgress = quizCacheService.inProgress(spaceId, memberId);
        if (!inProgress) {
            List<Long> itemIds = quizReadService.getQuizIds(spaceId, type);
            quizCacheService.start(spaceId, memberId, itemIds);
        }

        Long randomQuizId = quizCacheService.pickRandomQuizId(spaceId, memberId);
        QuizResponse quizResponse = quizReadService.getQuiz(ReadQuiz.of(memberDetails.getMember(), randomQuizId));
        return ResponseEntity.ok(quizResponse);
    }
}
