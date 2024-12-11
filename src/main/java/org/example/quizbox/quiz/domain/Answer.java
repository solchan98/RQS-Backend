package org.example.quizbox.quiz.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "correct")
    private boolean correct;

    public Answer(String content, boolean correct) {
        this.content = content;
        this.correct = correct;
    }

    public static Answer trueOption(String content) {
        return new Answer(content, true);
    }

    public static Answer falseOption(String content) {
        return new Answer(content, false);
    }
}
