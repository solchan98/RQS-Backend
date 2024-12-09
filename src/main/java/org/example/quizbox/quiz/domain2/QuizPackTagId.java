package org.example.quizbox.quiz.domain2;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Embeddable
public class QuizPackTagId implements Serializable {

    private Long quizPackId;
    private Long tagId;

    // 기본 생성자
    public QuizPackTagId() {
    }

    public QuizPackTagId(Long quizPackId, Long tagId) {
        this.quizPackId = quizPackId;
        this.tagId = tagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuizPackTagId that = (QuizPackTagId) o;
        return Objects.equals(quizPackId, that.quizPackId) &&
                Objects.equals(tagId, that.tagId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quizPackId, tagId);
    }
}