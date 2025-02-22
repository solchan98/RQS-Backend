package com.example.autoquizbox.entities;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutoQuizTaskHistoryRepository extends JpaRepository<AutoQuizTaskHistory, Long> {

    Optional<AutoQuizTaskHistory> findByIdAndUserId(long id, long userID);

    List<AutoQuizTaskHistory> findByUserIdAndTaskStatusIn(long userId, List<TaskStatus> taskStatuses);
}
