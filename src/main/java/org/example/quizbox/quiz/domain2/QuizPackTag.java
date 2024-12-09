package org.example.quizbox.quiz.domain2;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class QuizPackTag {

    @EmbeddedId
    private QuizPackTagId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("quizPackId")
    @JoinColumn(name = "quiz_pack_id")
    private QuizPack quizPack;

    public long getTagId() {
        return id.getTagId();
    }
}
