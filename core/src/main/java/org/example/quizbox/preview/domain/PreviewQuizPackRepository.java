package org.example.quizbox.preview.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PreviewQuizPackRepository extends JpaRepository<PreviewQuizPack, Long> {

    Optional<PreviewQuizPack> findByIdAndUserId(long id, long userId);
    List<PreviewQuizPack> findAllByUserId(long userId);
    List<PreviewQuizPack> findAllByUserIdAndTaskIdIsNull(long userId);
    List<PreviewQuizPack> findAllByUserIdAndTaskIdIsNotNull(long userId);
}
