package com.example.rqs.core.space.repository;

import com.example.rqs.core.config.DataTestConfig;
import com.example.rqs.core.member.Member;
import com.example.rqs.core.member.repository.MemberRepository;
import com.example.rqs.core.space.Space;
import com.example.rqs.core.space.SpaceMember;
import com.example.rqs.core.space.service.dtos.TSpaceResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Import(DataTestConfig.class)
@DataJpaTest(showSql = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("스페이스 레포지토리 테스트")
public class SpaceRepositoryTest {

    @Autowired
    private SpaceMemberRepository spaceMemberRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private SpaceRepository spaceRepository;

    private LocalDateTime testCursorLocalDateTime; // 3번째 스페이스의 createdAt

    @BeforeAll
    void init() {
        List<Space> testSpaceList = new ArrayList<>();
        List<Member> testMemberList = new ArrayList<>();
        List<SpaceMember> testSpaceMemberList = new ArrayList<>();
        for (int idx = 0; idx < 5; idx++) {
            Space space = Space.newSpace("space" + idx, true);
            testSpaceList.add(space);
            if (idx == 2) testCursorLocalDateTime = space.getCreatedAt();
            for (int jdx = 0; jdx < idx + 1; jdx++) {
                Member member = Member.newMember("member" + jdx, "1234", "nickname" + jdx);
                testMemberList.add(member);
                SpaceMember spaceMember = SpaceMember.newSpaceAdmin(member, space);
                testSpaceMemberList.add(spaceMember);
            }
        }
        memberRepository.saveAll(testMemberList);
        spaceRepository.saveAll(testSpaceList);
        spaceMemberRepository.saveAll(testSpaceMemberList);
    }

    @Test
    @DisplayName("getSpaceList - 정상 조회")
    void getSpaceListTest() {
        List<TSpaceResponse> spaceList = spaceRepository.getSpaceList(null);

        assertAll(
                () -> assertThat(spaceList.size()).isEqualTo(5),
                () -> assertThat(spaceList.get(0).getTitle()).isEqualTo("space4"),
                () -> assertThat(spaceList.get(0).getSpaceMemberCount()).isEqualTo(5L),
                () -> assertThat(spaceList.get(4).getTitle()).isEqualTo("space0"),
                () -> assertThat(spaceList.get(4).getSpaceMemberCount()).isEqualTo(1L)
        );
    }

    @Test
    @DisplayName("getSpaceList - 특정 기준 페이징")
    void getSpaceListWithCursorPagingTest() {
        List<TSpaceResponse> spaceList = spaceRepository.getSpaceList(testCursorLocalDateTime);

        assertAll(
                () -> assertThat(spaceList.size()).isEqualTo(2),
                () -> assertThat(spaceList.get(0).getTitle()).isEqualTo("space1"),
                () -> assertThat(spaceList.get(0).getSpaceMemberCount()).isEqualTo(2L),
                () -> assertThat(spaceList.get(1).getTitle()).isEqualTo("space0"),
                () -> assertThat(spaceList.get(1).getSpaceMemberCount()).isEqualTo(1L)
        );
    }
}
