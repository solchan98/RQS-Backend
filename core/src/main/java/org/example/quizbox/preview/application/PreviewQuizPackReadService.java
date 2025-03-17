package org.example.quizbox.preview.application;

import lombok.RequiredArgsConstructor;
import org.example.quizbox.preview.domain.PreviewQuizPack;
import org.example.quizbox.preview.domain.PreviewQuizPackRepository;
import org.example.quizbox.preview.domain.PreviewQuizPackType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PreviewQuizPackReadService {

    private final PreviewQuizPackRepository previewQuizPackRepository;

    public PreviewQuizPack query(long userId, long previewQuizPackId) {
        return previewQuizPackRepository.findByIdAndUserId(previewQuizPackId, userId)
                // TODO
                .orElseThrow(() -> new RuntimeException("오토 퀴즈팩을 찾을 수 없습니다."));
    }

    public List<PreviewQuizPack> queryAll(long userId, PreviewQuizPackType previewQuizPackType) {
        if (Objects.isNull(previewQuizPackType)) {
            return previewQuizPackRepository.findAllByUserId(userId);
        }

        if (PreviewQuizPackType.AUTO == previewQuizPackType) {
            return previewQuizPackRepository.findAllByUserIdAndTaskIdIsNotNull(userId);
        }

        return previewQuizPackRepository.findAllByUserIdAndTaskIdIsNull(userId);
    }
}
