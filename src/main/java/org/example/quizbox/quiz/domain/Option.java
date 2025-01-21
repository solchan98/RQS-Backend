package org.example.quizbox.quiz.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "quiz_option")
@Getter
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "correct")
    private boolean correct;

    public Option(String content, boolean correct) {
        this.content = content;
        this.correct = correct;
    }

    public static Option trueOption(String content) {
        return new Option(content, true);
    }

    public static Option falseOption(String content) {
        return new Option(content, false);
    }
}
