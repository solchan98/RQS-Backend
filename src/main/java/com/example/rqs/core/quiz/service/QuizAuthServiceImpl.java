package com.example.rqs.core.quiz.service;

import com.example.rqs.core.quiz.Quiz;
import com.example.rqs.core.quiz.repository.QuizRepository;
import com.example.rqs.core.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuizAuthServiceImpl implements QuizAuthService {

    private final QuizRepository quizRepository;

    @Override
    public boolean isQuizCreator(Member member, Long quizId) {
        Optional<Quiz> itemOptional = quizRepository.findById(quizId);
        if (itemOptional.isEmpty()) return false;
        return isQuizCreator(member, itemOptional.get());
    }

    @Override
    public boolean isQuizCreator(Member member, Quiz quiz) {
        return quiz.getSpaceMember().getMember().getMemberId().equals(member.getMemberId());
    }
}
