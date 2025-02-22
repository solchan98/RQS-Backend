package org.example.quizbox.quiz.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class QuizPackTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long tagId;

    public QuizPackTag(Long tagId) {
        this.tagId = tagId;
    }

    public long getTagId() {
        return tagId;
    }
}
