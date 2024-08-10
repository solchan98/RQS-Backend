package org.example.quizbox.quiz.domain;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;

@RequiredArgsConstructor
public class QuizPicker {

    private final IQuizPackRepository quizPackRepository;

    public Quiz getQuiz(long quizPackId, long quizId, long memberId) {
        Optional<QuizPackMember> optionalQuizPackMember = quizPackRepository.findQuizPackMemberByIdAndMemberId(
                quizPackId, memberId);
        if (optionalQuizPackMember.isEmpty()) {
            throw new BusinessException(ExceptionConstants.QP4);
        }

        return quizPackRepository.findQuizByIdAndQuizId(quizPackId, quizId)
                .orElseThrow(() -> new BusinessException(ExceptionConstants.QP6));
    }

}
