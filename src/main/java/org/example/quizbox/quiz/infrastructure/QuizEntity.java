package org.example.quizbox.quiz.infrastructure;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.quizbox.quiz.domain.Quiz;
import org.example.quizbox.quiz.domain.QuizAnswers;
import org.example.quizbox.quiz.domain.QuizContent;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class QuizEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", unique = true)
    private String content;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_pack_id")
    private QuizPackEntity quizPackEntity;

    @OneToMany(mappedBy = "quizEntity", cascade = CascadeType.ALL)
    private List<AnswerEntity> answerEntities = new ArrayList<>();

    public Quiz toDomain() {
        return new Quiz(id, new QuizContent(content),
                new QuizAnswers(
                        answerEntities.stream().filter(AnswerEntity::isCorrect).map(AnswerEntity::toDomain).collect(
                                Collectors.toSet()),
                        answerEntities.stream().filter(answerEntity -> !answerEntity.isCorrect())
                                .map(AnswerEntity::toDomain).collect(Collectors.toSet())
                )
        );
    }

    public static QuizEntity fromDomain(Quiz quiz) {
        QuizEntity quizEntity = new QuizEntity();

        quizEntity.id = quiz.getId();
        quizEntity.content = quiz.getContent().value();
        if (quiz.getQuizAnswers() != null) {
            QuizAnswers quizAnswers = quiz.getQuizAnswers();
            quizEntity.answerEntities = Stream.concat(
                    quizAnswers.correctAnswers().stream().map(answer -> AnswerEntity.fromDomain(answer, true)),
                    quizAnswers.incorrectAnswers().stream().map(answer -> AnswerEntity.fromDomain(answer, false))
            ).toList();
            quizEntity.answerEntities.forEach(answerEntity -> answerEntity.setQuizEntity(quizEntity));
        }

        return quizEntity;
    }

}
