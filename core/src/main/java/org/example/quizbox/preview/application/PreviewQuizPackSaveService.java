package org.example.quizbox.preview.application;

import lombok.RequiredArgsConstructor;
import org.example.quizbox.preview.domain.PreviewQuizPack;
import org.example.quizbox.preview.domain.PreviewQuizPackRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class PreviewQuizPackSaveService {

    private final PreviewQuizPackRepository previewQuizPackRepository;

    public PreviewQuizPack command(Long userId, final PreviewQuizPack previewQuizPack) {
        if (Objects.isNull(previewQuizPack.getId())) {
            previewQuizPack.setUserId(userId);
            return previewQuizPackRepository.save(previewQuizPack);
        }

        PreviewQuizPack savedPreviewQuizPack = previewQuizPackRepository.findById(previewQuizPack.getId())
                .orElseThrow(() -> new RuntimeException("프리뷰 퀴즈팩 없음"));
        savedPreviewQuizPack.update(userId, previewQuizPack);

        return previewQuizPackRepository.save(savedPreviewQuizPack);
    }
}
