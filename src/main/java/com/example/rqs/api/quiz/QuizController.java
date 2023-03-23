package com.example.rqs.api.quiz;

import com.example.rqs.api.cache.quiz.QuizCacheService;
import com.example.rqs.api.common.CommonAPI;
import com.example.rqs.api.exception.Message;
import com.example.rqs.api.jwt.MemberDetails;
import com.example.rqs.core.quiz.service.*;
import com.example.rqs.core.quiz.service.dtos.*;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class QuizController {
    private static final String AUTH = "/my";
    private static final String DOMAIN = "/quiz";

    private final QuizReadService quizReadService;
    private final QuizAuthService quizAuthService;
    private final QuizCacheService quizCacheService;
    private final QuizUpdateService quizUpdateService;
    private final QuizRegisterService quizRegisterService;

    private final CreateQuizValidator createQuizValidator;
    private final UpdateQuizValidator updateQuizValidator;

    @InitBinder("createQuizDto")
    public void initCreateQuizBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(createQuizValidator);
    }

    @InitBinder("updateQuizDto")
    public void initUpdateQuizBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(updateQuizValidator);
    }

    @PostMapping(AUTH + DOMAIN)
    public QuizResponse createNewQuiz(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody CreateQuizDto createQuizDto
    ) {
        CreateQuiz createQuiz = CreateQuiz.of(
                createQuizDto.getSpaceId(),
                memberDetails.getMember(),
                createQuizDto.getQuestion(),
                createQuizDto.getCreateAnswers(),
                createQuizDto.getType(),
                createQuizDto.getHint());
        return quizRegisterService.createQuiz(createQuiz);
    }

    @PostMapping(AUTH + DOMAIN + "/{parentId}/child")
    public QuizResponse createNewChildQuiz(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable("parentId") Long parentId,
            @Validated @RequestBody CreateQuizDto createQuizDto
    ) {
        CreateQuiz createQuiz = CreateQuiz.of(
                createQuizDto.getSpaceId(),
                memberDetails.getMember(),
                createQuizDto.getQuestion(),
                createQuizDto.getCreateAnswers(),
                createQuizDto.getType(),
                createQuizDto.getHint());
        return quizRegisterService.createChildQuiz(createQuiz, parentId);
    }

    @GetMapping(DOMAIN + "/{quizId}")
    @CommonAPI
    public QuizResponse getQuiz(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable("quizId") Long quizId
    ) {
        if (memberDetails == null) {
            return quizReadService.getQuiz(ReadQuiz.of(quizId));
        }

        return quizReadService.getQuiz(ReadQuiz.of(memberDetails.getMember(), quizId));
    }

    @GetMapping(DOMAIN + "/all")
    @CommonAPI
    public List<QuizResponse> getQuizzes(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("spaceId") Long spaceId,
            @Nullable @RequestParam("lastQuizId") Long lastQuizId
    ) {
        if (memberDetails == null) {
            return quizReadService.getQuizzes(ReadQuizzes.of(lastQuizId, spaceId));
        }

        return quizReadService.getQuizzes(ReadQuizzes.of(memberDetails.getMember(), lastQuizId, spaceId));
    }

    @GetMapping(AUTH + DOMAIN + "/isCreator/{quizId}")
    public Message checkIsCreator(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable("quizId") Long quizId
    ) {
        boolean isQuizCreator = quizAuthService.isQuizCreator(memberDetails.getMember(), quizId);
        if (isQuizCreator) {
            return new Message("200", HttpStatus.OK);
        }

        return new Message("403", HttpStatus.FORBIDDEN);
    }

    @PutMapping(AUTH + DOMAIN + "/{quizId}")
    public QuizResponse updateQuiz(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable("quizId") Long quizId,
            @Validated @RequestBody UpdateQuizDto updateQuizDto
    ) {
        UpdateQuiz updateQuiz = UpdateQuiz.of(
                memberDetails.getMember(),
                quizId,
                updateQuizDto.getQuestion(),
                updateQuizDto.getAnswers(),
                updateQuizDto.getType(),
                updateQuizDto.getHint());
        return quizUpdateService.updateQuiz(updateQuiz);
    }

    @DeleteMapping(AUTH + DOMAIN + "/{quizId}")
    public DeleteResponse deleteItem(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable("quizId") Long quizId
    ) {
        DeletedQuizData deletedQuizData = quizUpdateService.deleteQuiz(memberDetails.getMember(), quizId);
        quizCacheService.deleteQuiz(deletedQuizData.getSpaceId(), quizId);
        return DeleteResponse.of(quizId, true);
    }
}
