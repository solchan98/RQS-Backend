package com.example.autoquizbox.application;

import com.example.autoquizbox.domain.AutoTask;
import com.example.autoquizbox.domain.AutoTaskRepository;
import com.example.autoquizbox.domain.vo.TaskStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AutoTaskReadAllService {

    private final AutoTaskRepository autoTaskRepository;

    public List<AutoTask> query(long userId, Set<TaskStatus> taskStatuses) {
        if (CollectionUtils.isEmpty(taskStatuses)) {
            return autoTaskRepository.findAllByUserId(userId);
        }

        return autoTaskRepository.findAllByUserIdAndTaskStatusIn(userId, taskStatuses);
    }
}
