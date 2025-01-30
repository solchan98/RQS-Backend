package org.example.quizbox.tag.infrastructure;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.example.quizbox.tag.domain.ITagRepository;
import org.example.quizbox.tag.domain.Tag;
import org.example.quizbox.tag.domain.Tags;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TagRepository implements ITagRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private final JpaTagRepository jpaTagRepository;

    @Override
    public Tags findAllByIds(Collection<Long> ids) {
        return new Tags(
                jpaTagRepository.findAllById(ids)
                        .stream()
                        .map(TagEntity::toDomain)
                        .collect(Collectors.toSet())
        );
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return jpaTagRepository.findByName(name)
                .map(TagEntity::toDomain);

    }

    @Override
    public Tag save(Tag tag) {
        String sql = """
                INSERT INTO tag_entity (id, name)
                VALUES (:id, :name)
                ON DUPLICATE KEY UPDATE name = :name
                """;

        entityManager.createNativeQuery(sql)
                .setParameter("id", tag.getId())
                .setParameter("name", tag.getName())
                .executeUpdate();

        return findByName(tag.getName())
                .orElseThrow(() -> new BusinessException(ExceptionConstants.SE1));
    }
}
