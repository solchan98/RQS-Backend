package org.example.quizbox.quiz.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private QuizContent content;

    @ManyToOne
    @JoinColumn(name = "quiz_pack_member_id")
    private QuizPackMember quizPackMember;

    @Embedded
    private Options options = new Options();

    public Quiz(QuizPackMember creator, QuizContent content, Set<Option> options) {
        this.content = content;
        this.quizPackMember = creator;
        this.options = new Options(options);
    }

    public boolean match(Set<Long> submitOptionIds) {
        return options.match(submitOptionIds);
    }

    public boolean isSameContent(Quiz newQuiz) {
        return this.content.equals(newQuiz.content);
    }
}
