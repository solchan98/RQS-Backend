package org.example.quizbox.subscriptions;

import org.example.quizbox.common.presentation.BasicResponse;
import org.example.quizbox.quiz.presentation.AddedTopicQuizResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Random;
import java.util.Set;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionsController {

    @GetMapping("/tags")
    public ResponseEntity<BasicResponse<SubscriptionTagsResponse>> getTags(
            @RequestParam("filterType") String filterType
    ) {
        if (filterType.equals("NEW_QUIZZES")) {
            return ResponseEntity.ok(new BasicResponse<>(SubscriptionTagsResponse.dummyNewQuizzes()));
        }

        if (filterType.equals("ALL")) {
            return ResponseEntity.ok(new BasicResponse<>(SubscriptionTagsResponse.dummy()));
        }
        return ResponseEntity.ok(new BasicResponse<>(null));
    }

    @GetMapping("/quizzes")
    public ResponseEntity<BasicResponse<Collection<AddedTopicQuizResponse>>> getAddedTopicsQuizzes(
            @RequestParam("filterType") String filterType
    ) {
        boolean randomBoolean = new Random().nextBoolean();
        if (randomBoolean) {
            return ResponseEntity.ok(new BasicResponse<>(AddedTopicQuizResponse.dummy()));
        }
        return ResponseEntity.ok(new BasicResponse<>(null));
    }
}
