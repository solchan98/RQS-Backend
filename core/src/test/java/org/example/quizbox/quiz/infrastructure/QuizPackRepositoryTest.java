package org.example.quizbox.quiz.infrastructure;

import org.example.quizbox.common.Pagination;
import org.example.quizbox.quiz.domain.*;
import org.example.quizbox.support.QuizPackBuilder;
import org.example.quizbox.support.infrastructure.DBTest;
import org.hibernate.annotations.processing.SQL;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.quizbox.support.QuizPackBuilder.quizPackBuilder;

@DBTest
class QuizPackRepositoryTest {

    @Autowired
    private QuizPackRepository quizPackRepository;

    private static int nextQuizPackId = 0;

    public int getNextQuizPackId(int count) {
        nextQuizPackId += count;

        return nextQuizPackId;
    }

    public void incrementNextQuizPackId(int count) {
        nextQuizPackId += count;
    }

    @Test
    void save() {
        long adminId = 1L;
        QuizPackMember quizPackMember = new QuizPackMember(adminId, QuizPackMemberRole.ADMIN);
        Quiz quiz = new Quiz(
                quizPackMember,
                new QuizContent("test quiz 1"),
                Set.of(
                Option.trueOption("1"),
                        Option.falseOption("2")
                )
        );
        QuizPack quizPack = QuizPack.of(
                "test",
                new QuizPackMembers(
                        Set.of(quizPackMember)
                ),
                new Quizzes(
                        Set.of(
                                quiz
                        )
                ),
                new QuizPackKeywords(Set.of())
        );

        quizPackRepository.save(quizPack);

        assertThat(quizPack.getId()).isNotNull();
    }
//
//    @Test
//    void findById() {
//        QuizPack quizPack = new QuizPack("test", Set.of(1L), Set.of(1L));
//        incrementNextQuizPackId(1);
//        quizPackRepository.save(quizPack);
//
//        assertThat(quizPackRepository.findById(quizPack.getId()))
//                .isNotEmpty().get()
//                .isEqualTo(quizPack);
//    }

//    @Test
//    void findAll() {
//        long admin1Id = 1L;
//        int admin1QuizPackSize = IntStream.range(nextQuizPackId, getNextQuizPackId(2))
//                .mapToObj(index -> new QuizPack("test".concat(String.valueOf(index)), Set.of(admin1Id), Set.of(1L)))
//                .map(quizPackRepository::save)
//                .collect(Collectors.toSet())
//                .size();
//        long admin2Id = 2L;
//        int admin2QuizPackSize = IntStream.range(nextQuizPackId, getNextQuizPackId(1))
//                .mapToObj(index -> new QuizPack("test".concat(String.valueOf(index)), Set.of(admin2Id), Set.of(2L)))
//                .map(quizPackRepository::save)
//                .collect(Collectors.toSet())
//                .size();
//
//        assertThat(quizPackRepository.findAll()).hasSize(admin1QuizPackSize + admin2QuizPackSize);
//    }
//
//    @Test
//    void findAllByMemberId() {
//        long admin1Id = 1L;
//        Set<QuizPack> quizPacks = IntStream.range(nextQuizPackId, getNextQuizPackId(2))
//                .mapToObj(index -> new QuizPack("test".concat(String.valueOf(index)), Set.of(admin1Id), Set.of(1L)))
//                .map(quizPackRepository::save)
//                .collect(Collectors.toSet());
//        long admin2Id = 2L;
//        IntStream.range(nextQuizPackId, getNextQuizPackId(1))
//                .mapToObj(index -> new QuizPack("test".concat(String.valueOf(index)), Set.of(admin2Id), Set.of(2L)))
//                .forEach(quizPackRepository::save);
//
//        assertThat(quizPackRepository.findAllBy(1L)).hasSize(quizPacks.size());
//    }
//
//    /**
//     * total : 30
//     */
//    @Test
//    void findAllByPaginationAndPublished() {
//        long admin1Id = 1L;
//        IntStream.range(nextQuizPackId, getNextQuizPackId(10))
//                .mapToObj(index -> new QuizPack("test".concat(String.valueOf(index)), Set.of(admin1Id), Set.of(1L)))
//                .forEach(quizPackRepository::save);
//        long admin2Id = 2L;
//        long expectedLastId = nextQuizPackId + 1;
//        IntStream.range(nextQuizPackId, getNextQuizPackId(20))
//                .mapToObj(index -> new QuizPack("test".concat(String.valueOf(index)), Set.of(admin2Id), Set.of(2L)))
//                .forEach(quizPackRepository::save);
//
//        Pagination firstRequestPagination = new Pagination(null, 20, false);
//        Pagination secondRequestPagination = new Pagination(expectedLastId, 20, false);
//
//        assertThat(quizPackRepository.findAllBy(firstRequestPagination)).hasSize(20);
//        assertThat(quizPackRepository.findAllBy(secondRequestPagination)).hasSize(10);
//    }
//
//    @Test
//    void findAllByPaginationAndPublishedIsFalse() {
//        long admin1Id = 1L;
//        IntStream.range(nextQuizPackId, getNextQuizPackId(10))
//                .mapToObj(index -> new QuizPack("test".concat(String.valueOf(index)), Set.of(admin1Id), Set.of(1L)))
//                .forEach(quizPackRepository::save);
//        long admin2Id = 2L;
//        IntStream.range(nextQuizPackId, getNextQuizPackId(20))
//                .mapToObj(index -> new QuizPack("test".concat(String.valueOf(index)), Set.of(admin2Id), Set.of(2L)))
//                .forEach(quizPackRepository::save);
//
//        Pagination firstRequestPagination = new Pagination(null, 20, false);
//
//        assertThat(quizPackRepository.findAllBy(firstRequestPagination)).hasSize(20);
//    }
}
