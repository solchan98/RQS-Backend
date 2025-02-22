package com.example.autoquizbox.service.query;

import com.example.autoquizbox.entities.AutoQuizPack;
import com.example.autoquizbox.entities.AutoQuizPackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutoQuizQueryService {

    private final AutoQuizPackRepository autoQuizPackRepository;

    public AutoQuizPack query(long userId, long taskId) {
        return autoQuizPackRepository.findByUserIdAndTaskId(userId, taskId)
                .orElseGet(() -> null);
    }
}
