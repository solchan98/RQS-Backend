package org.example.quizbox.quiz.domain;

import org.example.quizbox.tag.domain.Tags;

import java.time.LocalDateTime;

public record QuizPackStatus(
        long quizPackId,
        String quizPackTitle,
        long quizPackMemberCount,
        long quizCount,
        boolean published,
        Tags tags,
        LocalDateTime createdAt
) {

    public static QuizPackStatus from(QuizPack quizPack, Tags tags) {
        return new QuizPackStatus(
                quizPack.getId(),
                quizPack.getTitle(),
                quizPack.memberSize(),
                quizPack.quizSize(),
                quizPack.isPublished(),
                tags,
                quizPack.getCreatedAt()
        );
    }

}
