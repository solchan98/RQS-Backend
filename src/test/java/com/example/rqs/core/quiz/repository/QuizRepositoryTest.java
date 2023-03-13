package com.example.rqs.core.quiz.repository;

import com.example.rqs.core.config.DataTestConfig;
import com.example.rqs.core.quiz.Quiz;
import com.example.rqs.core.quiz.service.dtos.QuizResponse;
import com.example.rqs.core.member.Member;
import com.example.rqs.core.member.repository.MemberRepository;

import com.example.rqs.core.space.*;
import com.example.rqs.core.space.repository.*;

import com.example.rqs.core.spacemember.SpaceMember;
import com.example.rqs.core.spacemember.repository.SpaceMemberRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(DataTestConfig.class)
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("아이템 레포지토리 테스트")
public class QuizRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private SpaceMemberRepository spaceMemberRepository;

    private SpaceMember spaceMember;

    void setUpSpaceMember() {
        Member member = Member.newMember("sol@sol.com", "1234", "sol");
        memberRepository.save(member);
        Space space = Space.newSpace("Test Space", Boolean.FALSE);
        spaceRepository.save(space);

        spaceMember = SpaceMember.newSpaceAdmin(member, space);
        spaceMemberRepository.save(spaceMember);
    }

    void createItems(SpaceMember spaceMember) {
        List<Quiz> quizList = new ArrayList<>(30);
        for (int idx = 0; idx < 30; idx++) {
            Quiz quiz = Quiz.newQuiz(spaceMember, "Question_" + idx, "Answer", "");
            quizList.add(quiz);
        }
        quizRepository.saveAll(quizList);
    }

    @BeforeAll
    void init() {
        setUpSpaceMember();
        createItems(spaceMember);
    }

    @AfterAll
    void clear() {
        quizRepository.deleteAllInBatch();
        spaceMemberRepository.deleteAllInBatch();
        spaceRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("getQuizList - 첫 조회 시")
    void getItemListTestWhenFirstRead() {
        Space space = spaceMember.getSpace();

        List<QuizResponse> quizzes = quizRepository.getQuizzes(space.getSpaceId(), null);

        assertThat(quizzes.size()).isEqualTo(20);
    }

    @Test
    @DisplayName("getQuizList - 두 번째 조회")
    void getItemListTestWhenSecondRead() {
        Space space = spaceMember.getSpace();

        List<QuizResponse> firstRead = quizRepository.getQuizzes(space.getSpaceId(), null);
        Long lastId = firstRead.get(firstRead.size() - 1).getQuizId();
        List<QuizResponse> items = quizRepository.getQuizzes(space.getSpaceId(), lastId);

        assertThat(items.size()).isEqualTo(10);
    }

    @Test
    @DisplayName("countBySpaceId - 정상 조회")
    void countBySpaceIdTest() {
        Space space = spaceMember.getSpace();
        // init()을 통해 최초로 Quiz 30개를 생성 (동일 스페이스)

        Long itemCount = quizRepository.countBySpaceId(space.getSpaceId());

        assertThat(itemCount).isEqualTo(30L);
    }

    @Test
    @DisplayName("countBySpaceId - 존재하지 않는 스페이스인 경우")
    void countBySpaceIdTestWhenIsEmpty() {

        Long itemCount = quizRepository.countBySpaceId(999L);

        assertThat(itemCount).isEqualTo(0L);
    }

    @Test
    @DisplayName("getItem(spaceId) - 정상 조회")
    void getItemBySpaceIdAndRandomIdxTest() {
        Space space = spaceMember.getSpace();
        List<Long> itemIds = quizRepository.getQuizIds(space.getSpaceId());

        QuizResponse quizResponse = quizRepository.getQuiz(itemIds.get(0));

        assertThat(quizResponse).isNotNull();
    }

    @Test
    @DisplayName("getItemIdList(spaceId) - 정상 조회")
    void getItemIdListTest() {
        Space space = spaceMember.getSpace();

        List<Long> itemIds = quizRepository.getQuizIds(space.getSpaceId());

        assertThat(itemIds.size()).isEqualTo(30L);
    }

}
