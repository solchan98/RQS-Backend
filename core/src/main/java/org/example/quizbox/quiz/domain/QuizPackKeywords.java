package org.example.quizbox.quiz.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class QuizPackKeywords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long keywordId;

    public QuizPackKeywords(Long keywordId) {
        this.keywordId = keywordId;
    }

    public long getKeywordId() {
        return keywordId;
    }
}
