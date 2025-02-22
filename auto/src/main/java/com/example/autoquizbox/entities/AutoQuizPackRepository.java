package com.example.autoquizbox.entities;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AutoQuizPackRepository extends JpaRepository<AutoQuizPack, Long> {

    Optional<AutoQuizPack> findByIdAndUserId(long id, long userID);

    Optional<AutoQuizPack> findByTaskId(long taskId);

    Optional<AutoQuizPack> findByUserIdAndTaskId(long userId, long taskId);

    List<AutoQuizPack> findByTaskIdIn(Set<Long> taskIds);
}
