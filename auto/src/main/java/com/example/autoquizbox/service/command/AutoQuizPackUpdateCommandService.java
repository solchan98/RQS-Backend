package com.example.autoquizbox.service.command;

import com.example.autoquizbox.common.BusinessException;
import com.example.autoquizbox.entities.AutoQuizPack;
import com.example.autoquizbox.entities.AutoQuizPackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AutoQuizPackUpdateCommandService {

    private final AutoQuizPackRepository autoQuizPackRepository;

    @Transactional
    public AutoQuizPack command(long userId, AutoQuizPack updateAutoQuizPack) {
        AutoQuizPack autoQuizPack = autoQuizPackRepository.findByIdAndUserId(updateAutoQuizPack.getId(), userId)
                .orElseThrow(() -> new BusinessException(404, "오토 퀴즈팩을 찾을 수 없습니다.(04)"));

        autoQuizPack.update(userId, updateAutoQuizPack);
        autoQuizPackRepository.save(autoQuizPack);

        return autoQuizPack;
    }
}
