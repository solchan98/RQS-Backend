package org.example.quizbox.quiz.domain2;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private QuizContent content;

    @ManyToOne
    private QuizPackMember quizPackMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_pack_id")
    private QuizPack quizPack;

    @Embedded
    private QuizAnswers answers = new QuizAnswers();

    public static Quiz create(QuizPack quizPack, QuizPackMember creator, QuizContent content, QuizAnswers quizAnswers) {
        return new Quiz(null, content, creator, quizPack, quizAnswers);
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
