package org.example.quizbox.quiz.domain;

import org.example.quizbox.keyword.domain.Keywords;

import java.time.LocalDateTime;

public record QuizPackStatus(
        long quizPackId,
        String quizPackTitle,
        long quizPackMemberCount,
        long quizCount,
        Keywords keywords,
        LocalDateTime createdAt
) {

    public static QuizPackStatus from(QuizPack quizPack, Keywords keywords) {
        return new QuizPackStatus(
                quizPack.getId(),
                quizPack.getTitle(),
                quizPack.memberSize(),
                quizPack.quizSize(),
                keywords,
                quizPack.getCreatedAt()
        );
    }

}
