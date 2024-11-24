package org.example.quizbox.quiz.infrastructure;

import jakarta.persistence.*;

@Entity
public class QuizPackTagEntity {

    @EmbeddedId
    private QuizPackTagId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("quizPackId")
    @JoinColumn(name = "quiz_pack_id")
    private QuizPackEntity quizPackEntity;

    public long getTagId() {
        return id.getTagId();
    }
}
