package com.example.autoquizbox.service.command;

import com.example.autoquizbox.common.BusinessException;
import com.example.autoquizbox.entities.AutoQuizPack;
import com.example.autoquizbox.entities.AutoQuizPackRepository;
import com.example.autoquizbox.entities.AutoQuizTaskHistory;
import com.example.autoquizbox.entities.AutoQuizTaskHistoryRepository;
import com.example.autoquizbox.infrastructure.core.CoreApi;
import com.example.autoquizbox.infrastructure.core.CoreApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AutoQuizPackPublishCommandService {

    private final CoreApi coreApi;

    private final AutoQuizPackRepository autoQuizPackRepository;
    private final AutoQuizTaskHistoryRepository autoQuizTaskHistoryRepository;

    @Transactional
    public long command(long userId, long autoQuizPackId) {
        AutoQuizPack autoQuizPack = autoQuizPackRepository.findByIdAndUserId(autoQuizPackId, userId)
                .orElseThrow(() -> new BusinessException(404, "요청 정보 확인 필요(AutoQuizPackId)"));
        AutoQuizTaskHistory autoQuizTaskHistory = autoQuizTaskHistoryRepository.findById(autoQuizPack.getTaskId())
                .orElseThrow(() -> new BusinessException(404, "요청 정보 확인 필요(TaskId)"));

        CoreApiResponse<Long> result = coreApi.commandCreateSimpleQuizPack(userId, autoQuizPack);

        if (!result.getData().equals(0L)) {
            autoQuizTaskHistory.publish();
            autoQuizPackRepository.delete(autoQuizPack);
        }

        return result.getData();
    }
}
