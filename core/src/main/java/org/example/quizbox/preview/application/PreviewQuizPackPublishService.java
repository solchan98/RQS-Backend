package org.example.quizbox.preview.application;


import lombok.RequiredArgsConstructor;
import org.example.quizbox.preview.domain.PreviewQuizPack;
import org.example.quizbox.preview.domain.PreviewQuizPackRepository;
import org.example.quizbox.quiz.application.CreateSimpleQuizPack;
import org.example.quizbox.quiz.application.QuizPackService;
import org.example.quizbox.quiz.domain.QuizPack;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PreviewQuizPackPublishService {

    private final QuizPackService quizPackService;

    private final PreviewQuizPackRepository previewQuizPackRepository;

    public QuizPack command(long userId, long previewQuizPackId) {
        PreviewQuizPack previewQuizPack = previewQuizPackRepository.findById(previewQuizPackId)
                .orElseThrow(() -> new RuntimeException("프리뷰 퀴즈팩 없음"));

        QuizPack quizPack = quizPackService.create(userId, toCreateSimpleQuizPack(previewQuizPack));
        previewQuizPackRepository.delete(previewQuizPack);

        return quizPack;
    }

    private CreateSimpleQuizPack toCreateSimpleQuizPack(PreviewQuizPack previewQuizPack) {
        return new CreateSimpleQuizPack(
                previewQuizPack.getTitle(),
                previewQuizPack.getQuizzes().stream()
                        .map(previewQuiz -> new CreateSimpleQuizPack.CreateSimpleQuiz(
                                        previewQuiz.getContent(),
                                        previewQuiz.getOptions()
                                                .stream()
                                                .map(previewOption -> new CreateSimpleQuizPack.CreateSimpleOption(
                                                                previewOption.getContent(),
                                                                previewOption.isCorrect()
                                                        )
                                                ).toList()
                                )
                        ).toList()
                ,
                previewQuizPack.getKeywords()
        );
    }
}
