package org.example.quizbox.tag.infrastructure;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.example.quizbox.tag.domain.ITagRepository;
import org.example.quizbox.tag.domain.Tag;
import org.example.quizbox.tag.domain.Tags;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
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
        return Optional.empty();
    }

    @Override
    public Tags findAllByNames(Collection<String> names) {
        return null;
    }

    @Override
    public Tag save(Tag tag) {
        return null;
    }

    @Override
    public Tags saveAll(Tags tags) {
        Set<TagEntity> tagEntities = tags.getValues()
                .stream()
                .map(TagEntity::from)
                .collect(Collectors.toSet());

        return new Tags(
                jpaTagRepository.saveAll(tagEntities)
                        .stream()
                        .map(TagEntity::toDomain)
                        .collect(Collectors.toSet())
        );
    }
}
