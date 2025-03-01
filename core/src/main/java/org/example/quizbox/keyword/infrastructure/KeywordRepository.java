package org.example.quizbox.keyword.infrastructure;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.example.quizbox.common.BusinessException;
import org.example.quizbox.common.ExceptionConstants;
import org.example.quizbox.keyword.domain.IKeywordRepository;
import org.example.quizbox.keyword.domain.Keyword;
import org.example.quizbox.keyword.domain.Keywords;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class KeywordRepository implements IKeywordRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private final JpaKeywordRepository jpaKeywordRepository;

    @Override
    public Keywords findAllByIds(Collection<Long> ids) {
        return new Keywords(jpaKeywordRepository.findAllById(ids));
    }

    @Override
    public Optional<Keyword> findByName(String name) {
        return jpaKeywordRepository.findByName(name);
    }

    @Override
    public Keyword save(Keyword keyword) {
        String sql = """
                INSERT INTO keyword (name)
                VALUES (:name)
                ON DUPLICATE KEY UPDATE name = :name
                """;

        entityManager.createNativeQuery(sql)
                .setParameter("name", keyword.getName())
                .executeUpdate();

        return findByName(keyword.getName())
                .orElseThrow(() -> new BusinessException(ExceptionConstants.SE1));
    }
}
