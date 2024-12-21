package org.example.quizbox.quiz.infrastructure;

import org.example.quizbox.common.infrastructure.Pagination;
import org.example.quizbox.quiz.domain.QuizPack;
import org.example.quizbox.support.infrastructure.DBTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

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
        QuizPack quizPack = new QuizPack("test", Set.of(1L), Set.of(1L));
        incrementNextQuizPackId(1);
        quizPackRepository.save(quizPack);

        assertThat(quizPack.getId()).isNotNull();
    }

    @Test
    void findById() {
        QuizPack quizPack = new QuizPack("test", Set.of(1L), Set.of(1L));
        incrementNextQuizPackId(1);
        quizPackRepository.save(quizPack);

        assertThat(quizPackRepository.findById(quizPack.getId()))
                .isNotEmpty().get()
                .isEqualTo(quizPack);
    }

    @Test
    void findAll() {
        long admin1Id = 1L;
        int admin1QuizPackSize = IntStream.range(nextQuizPackId, getNextQuizPackId(2))
                .mapToObj(index -> new QuizPack("test".concat(String.valueOf(index)), Set.of(admin1Id), Set.of(1L)))
                .map(quizPackRepository::save)
                .collect(Collectors.toSet())
                .size();
        long admin2Id = 2L;
        int admin2QuizPackSize = IntStream.range(nextQuizPackId, getNextQuizPackId(1))
                .mapToObj(index -> new QuizPack("test".concat(String.valueOf(index)), Set.of(admin2Id), Set.of(2L)))
                .map(quizPackRepository::save)
                .collect(Collectors.toSet())
                .size();

        assertThat(quizPackRepository.findAll()).hasSize(admin1QuizPackSize + admin2QuizPackSize);
    }

    @Test
    void findAllByMemberId() {
        long admin1Id = 1L;
        Set<QuizPack> quizPacks = IntStream.range(nextQuizPackId, getNextQuizPackId(2))
                .mapToObj(index -> new QuizPack("test".concat(String.valueOf(index)), Set.of(admin1Id), Set.of(1L)))
                .map(quizPackRepository::save)
                .collect(Collectors.toSet());
        long admin2Id = 2L;
        IntStream.range(nextQuizPackId, getNextQuizPackId(1))
                .mapToObj(index -> new QuizPack("test".concat(String.valueOf(index)), Set.of(admin2Id), Set.of(2L)))
                .forEach(quizPackRepository::save);

        assertThat(quizPackRepository.findAllBy(1L)).hasSize(quizPacks.size());
    }

    /**
     * total : 30
     */
    @Test
    void findAllByPagination() {
        long admin1Id = 1L;
        IntStream.range(nextQuizPackId, getNextQuizPackId(10)) // 10개
                .mapToObj(index -> new QuizPack("test".concat(String.valueOf(index)), Set.of(admin1Id), Set.of(1L)))
                .forEach(quizPackRepository::save);
        long admin2Id = 2L;
        long expectedLastId = nextQuizPackId + 1;
        IntStream.range(nextQuizPackId, getNextQuizPackId(20)) // 10개
                .mapToObj(index -> new QuizPack("test".concat(String.valueOf(index)), Set.of(admin2Id), Set.of(2L)))
                .forEach(quizPackRepository::save);

        Pagination firstRequestPagination = new Pagination(null, 20, false);
        Pagination secondRequestPagination = new Pagination(expectedLastId, 20, false);

        assertThat(quizPackRepository.findAllBy(firstRequestPagination)).hasSize(20);
        assertThat(quizPackRepository.findAllBy(secondRequestPagination)).hasSize(10);
    }

    @Test
    void findAllByMemberIdAndPagination() {
        long admin1Id = 1L;
        IntStream.range(nextQuizPackId, getNextQuizPackId(20)) // 20개
                .mapToObj(index -> new QuizPack("test".concat(String.valueOf(index)), Set.of(admin1Id), Set.of(1L)))
                .forEach(quizPackRepository::save);
        long admin2Id = 2L;
        IntStream.range(nextQuizPackId, getNextQuizPackId(10)) // 10개
                .mapToObj(index -> new QuizPack("test".concat(String.valueOf(index)), Set.of(admin2Id), Set.of(2L)))
                .forEach(quizPackRepository::save);
        Pagination firstRequestPagination = new Pagination(null, 20, false);
        Pagination secondRequestPagination = new Pagination(null, 20, false);

        assertThat(quizPackRepository.findAllBy(admin1Id, firstRequestPagination)).hasSize(20);
        assertThat(quizPackRepository.findAllBy(admin2Id, secondRequestPagination)).hasSize(10);
        assertThat(quizPackRepository.findAllBy(-999L, firstRequestPagination)).isEmpty();
    }
}
