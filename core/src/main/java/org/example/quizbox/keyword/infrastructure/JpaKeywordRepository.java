package org.example.quizbox.keyword.infrastructure;

import org.example.quizbox.keyword.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaKeywordRepository extends JpaRepository<Keyword, Long> {

    Optional<Keyword> findByName(String name);
}
