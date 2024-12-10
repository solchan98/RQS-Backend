package org.example.quizbox.quiz.domain2;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class QuizContent {
    @Column(name = "content")
    private String value;

    public QuizContent(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
