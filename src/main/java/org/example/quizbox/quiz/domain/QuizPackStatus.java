package org.example.quizbox.quiz.domain;

import org.example.quizbox.tag.domain.Tags;
import org.example.quizbox.quiz.domain2.QuizPack;

public record QuizPackStatus(
        long quizPackId,
        String quizPackTitle,
        long quizPackMemberCount,
        long quizCount,
        Tags tags
) {

    public static QuizPackStatus from(QuizPack quizPack, Tags tags) {
        return new QuizPackStatus(
                quizPack.getId(),
                quizPack.getTitle(),
                quizPack.memberSize(),
                quizPack.quizSize(),
                tags
        );
    }

}
