package com.example.autoquizbox.domain;

import com.example.autoquizbox.domain.vo.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AutoTaskRepository extends JpaRepository<AutoTask, Long> {

    List<AutoTask> findAllByUserId(long userId);

    List<AutoTask> findAllByUserIdAndTaskStatusIn(long userId, Set<TaskStatus> taskStatuses);

    Optional<AutoTask> findByIdAndUserId(long id, long userId);
}
