package org.example.quizbox.quiz.infrastructure;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.quizbox.quiz.domain.Answer;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class AnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "correct")
    private boolean correct;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private QuizEntity quizEntity;

    public Answer toDomain() {
        return new Answer(id, content);
    }

    public static AnswerEntity fromDomain(Answer answer, boolean correct) {
        AnswerEntity answerEntity = new AnswerEntity();

        answerEntity.id = answer.getId();
        answerEntity.content = answer.getContent();
        answerEntity.correct = correct;

        return answerEntity;
    }
}
