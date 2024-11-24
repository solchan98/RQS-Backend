package org.example.quizbox.tag.domain;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface ITagRepository {

    Tags findAllByIds(Collection<Long> ids);

    Optional<Tag> findByName(String name);

    Tags findAllByNames(Collection<String> names);

    Tag save(Tag tag);

    Tags saveAll(Tags tags);
}
