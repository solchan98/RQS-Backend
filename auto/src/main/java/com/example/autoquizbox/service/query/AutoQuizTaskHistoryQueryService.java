package com.example.autoquizbox.service.query;

import com.example.autoquizbox.entities.*;
import com.example.autoquizbox.presentation.AutoQuizTaskStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AutoQuizTaskHistoryQueryService {

    private final AutoQuizTaskHistoryRepository autoQuizTaskHistoryRepository;

    private final AutoQuizPackRepository autoQuizPackRepository;

    @Transactional(readOnly = true)
    public List<AutoQuizTaskStatus> query(long userId, List<TaskStatus> taskStatuses) {
        List<AutoQuizTaskHistory> autoQuizTaskHistories =
                autoQuizTaskHistoryRepository.findByUserIdAndTaskStatusIn(userId, taskStatuses);
        Map<Long, AutoQuizPack> autoQuizPackMap = toMapAutoQuizPack(autoQuizTaskHistories);

        return autoQuizTaskHistories.stream()
                .map(history ->
                        new AutoQuizTaskStatus(
                                autoQuizPackMap.get(history.getId()).getTitle(),
                                history
                        )
                )
                .toList();
    }

    private Map<Long, AutoQuizPack> toMapAutoQuizPack(List<AutoQuizTaskHistory> autoQuizTaskHistories) {
        return autoQuizPackRepository.findByTaskIdIn(getTaskIds(autoQuizTaskHistories))
                .stream()
                .collect(Collectors.toMap(AutoQuizPack::getTaskId, Function.identity()));
    }

    private static Set<Long> getTaskIds(List<AutoQuizTaskHistory> autoQuizTaskHistories) {
        return autoQuizTaskHistories.stream().map(AutoQuizTaskHistory::getId).collect(Collectors.toSet());
    }
}
