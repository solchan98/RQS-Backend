package com.example.rqs.core.quiz;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_id", referencedColumnName = "quizId")
    private Quiz quiz;

    private String answer;

    private boolean isCorrect;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    private Answer(Quiz quiz, String answer, boolean isCorrect) {
        this.quiz = quiz;
        this.answer = answer;
        this.isCorrect = isCorrect;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public static Answer of(Quiz quiz, String answer, boolean isCorrect) {
        return new Answer(quiz, answer, isCorrect);
    }
}
