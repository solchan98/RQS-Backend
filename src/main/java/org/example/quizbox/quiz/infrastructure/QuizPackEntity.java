package org.example.quizbox.quiz.infrastructure;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.quizbox.quiz.domain.QuizPack;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class QuizPackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToMany(mappedBy = "quizPackEntity", cascade = CascadeType.ALL)
    private List<QuizEntity> quizzes = new ArrayList<>();

    public QuizPack toDomain() {
        return new QuizPack(id, title, new ArrayList<>(quizzes.stream().map(QuizEntity::toDomain).toList()));
    }

    public static QuizPackEntity fromDomain(QuizPack quizPack) {
        QuizPackEntity quizPackEntity = new QuizPackEntity();

        quizPackEntity.id = quizPack.getId();
        quizPackEntity.title = quizPack.getTitle();
        if (quizPack.getQuizzes() != null) {
            quizPackEntity.quizzes = quizPack.getQuizzes().stream().map(QuizEntity::fromDomain).toList();
            quizPackEntity.quizzes.forEach(quizEntity -> quizEntity.setQuizPackEntity(quizPackEntity));
        }

        return quizPackEntity;
    }
}
