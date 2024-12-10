package org.example.quizbox.quiz.domain2;

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
    private QuizPackMember quizPackMember;

    @Embedded
    private QuizAnswers answers = new QuizAnswers();

    public Quiz(QuizPackMember creator, QuizContent content, Set<Answer> answers) {
        this.content = content;
        this.quizPackMember = creator;
        this.answers = new QuizAnswers(answers);
    }

    public boolean isMatched(Set<Long> submitAnswerIds) {
        return answers.isMatchedCorrectAnswers(submitAnswerIds);
    }

    public boolean isSameContent(Quiz newQuiz) {
        return this.content.equals(newQuiz.content);
    }

    public boolean containsAllAnswers(Set<Long> answerIds) {
        return answers.containsAll(answerIds);
    }
}
