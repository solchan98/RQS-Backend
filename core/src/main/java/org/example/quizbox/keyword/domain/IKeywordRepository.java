package org.example.quizbox.keyword.domain;

import java.util.Collection;
import java.util.Optional;

public interface IKeywordRepository {

    Keywords findAllByIds(Collection<Long> ids);

    Optional<Keyword> findByName(String name);

    /**
     * find or create (upsert)
     *
     * @param keyword
     */
    Keyword save(Keyword keyword);

}
