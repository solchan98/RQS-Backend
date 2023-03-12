package com.example.rqs.api.item.randomquiz;

import com.example.rqs.api.cache.quiz.QuizCache;
import com.example.rqs.api.cache.quiz.QuizCacheService;
import com.example.rqs.api.jwt.MemberDetails;
import com.example.rqs.core.item.service.ItemReadService;
import com.example.rqs.core.item.service.dtos.ItemResponse;
import com.example.rqs.core.item.service.dtos.ReadItem;
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

    @GetMapping("/pick/{spaceId}")
    public ResponseEntity<ItemResponse> pickRandomQuiz(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable("spaceId") Long spaceId
    ) {
        Long memberId = memberDetails.getMember().getMemberId();
        boolean inProgress = quizCacheService.inProgress(spaceId, memberId);
        if (!inProgress) {
            List<Long> itemIds = itemReadService.getItemIds(spaceId);
            quizCacheService.start(spaceId, memberId, itemIds);
        }
        
        Long randomQuizId = quizCacheService.pickRandomQuizId(spaceId, memberId);
        ItemResponse itemResponse = itemReadService.getItem(ReadItem.of(randomQuizId));
        return ResponseEntity.ok(itemResponse);
    }
}
