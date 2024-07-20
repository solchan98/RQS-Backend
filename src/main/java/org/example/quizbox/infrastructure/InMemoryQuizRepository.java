package org.example.quizbox.infrastructure;

import static org.example.quizbox.infrastructure.InMemoryStorage.quizEntities;
import static org.example.quizbox.infrastructure.InMemoryStorage.quizEntitiesByGroupId;

import java.util.List;
import java.util.Set;
import org.example.quizbox.domain.Quiz;
import org.example.quizbox.domain.QuizRepository;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryQuizRepository implements QuizRepository {

    @Override
    public List<Quiz> findAllByGroupId(long groupId) {
        if (!quizEntitiesByGroupId.containsKey(groupId)) {
            return List.of();
        }
        Set<Long> quizIds = quizEntitiesByGroupId.get(groupId);

        return quizIds.stream()
                .map(quizEntities::get)
                .map(QuizEntity::toDomain)
                .toList();
    }
}
