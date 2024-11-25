package org.example.quizbox.tag.domain;

import java.util.Collection;
import java.util.Optional;

public interface ITagRepository {

    Tags findAllByIds(Collection<Long> ids);

    Optional<Tag> findByName(String name);

    Tag save(Tag tag);
}
