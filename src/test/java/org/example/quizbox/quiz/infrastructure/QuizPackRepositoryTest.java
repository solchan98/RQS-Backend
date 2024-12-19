package org.example.quizbox.quiz.infrastructure;

import jakarta.persistence.EntityManager;
import org.example.quizbox.quiz.domain.QuizPack;
import org.example.quizbox.support.infrastructure.DBTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DBTest
public class QuizPackRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private QuizPackRepository quizPackRepository;

    @Test
    void save() {
        QuizPack quizPack = new QuizPack("test", Set.of(1L), Set.of(1L));

        quizPackRepository.save(quizPack);

        assertThat(quizPack.getId()).isNotNull();
    }

    @Test
    void findById() {
        QuizPack quizPack = new QuizPack("test", Set.of(1L), Set.of(1L));
        quizPackRepository.save(quizPack);

        assertThat(quizPackRepository.findById(quizPack.getId()))
                .isNotEmpty().get()
                .isEqualTo(quizPack);
    }

    @Test
    void findAll() {
        long admin1Id = 1L;
        int admin1QuizPackSize = IntStream.range(0, 2)
                .mapToObj(index -> new QuizPack("test".concat(String.valueOf(index)), Set.of(admin1Id), Set.of(1L)))
                .map(quizPackRepository::save)
                .collect(Collectors.toSet())
                .size();
        long admin2Id = 2L;
        int admin2QuizPackSize = IntStream.range(3, 4)
                .mapToObj(index -> new QuizPack("test".concat(String.valueOf(index)), Set.of(admin2Id), Set.of(2L)))
                .map(quizPackRepository::save)
                .collect(Collectors.toSet())
                .size();

        assertThat(quizPackRepository.findAll()).hasSize(admin1QuizPackSize + admin2QuizPackSize);
    }

    @Test
    void findAllByMemberId() {
        long admin1Id = 1L;
        Set<QuizPack> quizPacks = IntStream.range(0, 2)
                .mapToObj(index -> new QuizPack("test".concat(String.valueOf(index)), Set.of(admin1Id), Set.of(1L)))
                .map(quizPackRepository::save)
                .collect(Collectors.toSet());
        long admin2Id = 2L;
        IntStream.range(3, 4)
                .mapToObj(index -> new QuizPack("test".concat(String.valueOf(index)), Set.of(admin2Id), Set.of(2L)))
                .forEach(quizPackRepository::save);

        assertThat(quizPackRepository.findAllByMemberId(1L)).hasSize(quizPacks.size());
    }
}
