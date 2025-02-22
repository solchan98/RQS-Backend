package com.example.autoquizbox.service.command;

import com.example.autoquizbox.entities.AutoQuizTaskHistory;
import com.example.autoquizbox.entities.AutoQuizTaskHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AutoQuizTaskHistoryCheckCommandService {

    private final AutoQuizTaskHistoryRepository autoQuizTaskHistoryRepository;

    @Transactional
    public void command(long userId, long taskId) {
        autoQuizTaskHistoryRepository.findByIdAndUserId(taskId, userId)
                .ifPresent(AutoQuizTaskHistory::review);
    }
}
