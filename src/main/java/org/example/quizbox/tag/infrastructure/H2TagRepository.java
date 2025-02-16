package org.example.quizbox.tag.infrastructure;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.example.quizbox.common.BusinessException;
import org.example.quizbox.common.ExceptionConstants;
import org.example.quizbox.tag.domain.ITagRepository;
import org.example.quizbox.tag.domain.Tag;
import org.example.quizbox.tag.domain.Tags;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class H2TagRepository implements ITagRepository {

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

    /**
     * save tag
     *
     * @param tag
     * @return the tag created or updated
     */
    @Override
    public Tag save(Tag tag) {
        String sql = "MERGE INTO tag_entity (name) KEY (name) VALUES (:name)";

        entityManager.createNativeQuery(sql)
                .setParameter("name", tag.getName())
                .executeUpdate();

        return findByName(tag.getName())
                .orElseThrow(() -> new BusinessException(ExceptionConstants.SE1));
    }
}
