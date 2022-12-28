package com.example.rqs.core.space.repository;

import com.example.rqs.core.config.DataTestConfig;
import com.example.rqs.core.member.Member;
import com.example.rqs.core.member.repository.MemberRepository;
import com.example.rqs.core.space.Space;
import com.example.rqs.core.spacemember.SpaceMember;
import com.example.rqs.core.space.service.dtos.SpaceMemberResponse;
import com.example.rqs.core.spacemember.repository.SpaceMemberRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Import(DataTestConfig.class)
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("스페이스(스페이스 멤버) 레포지토리 테스트")
public class SpaceMemberRepositoryTest {

    private final String firstSpaceMemberEmail = "0test@email.com";
    private final List<SpaceMember> spaceMemberList = new ArrayList<>(2);

    @Autowired
    private SpaceMemberRepository spaceMemberRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private SpaceRepository spaceRepository;

    @BeforeAll
    void init() {
        Space space = Space.newSpace("Test Space", false);
        spaceRepository.save(space);
        for (int idx = 0; idx < 2; idx++) {
            Member member = Member.newMember(idx + "test@email.com", "1234", idx + "sol");
            memberRepository.save(member);
            spaceMemberList.add(SpaceMember.newSpaceAdmin(member,space));
        }
        spaceMemberRepository.saveAll(spaceMemberList);
    }

    @Test
    @DisplayName("getSpaceMember - 정상 조회")
    void getSpaceMemberTest() {
        Member member = spaceMemberList.get(0).getMember();
        Space space = spaceMemberList.get(0).getSpace();
        Optional<SpaceMember> spaceMemberOptional = spaceMemberRepository.getSpaceMember(
                member.getMemberId(), space.getSpaceId());

        assertThat(spaceMemberOptional.isPresent()).isTrue();
        SpaceMember spaceMember = spaceMemberOptional.get();
        assertAll(
                () -> assertThat(spaceMember.getMember().getEmail()).isEqualTo(firstSpaceMemberEmail),
                () -> assertThat(spaceMember.getMember().getNickname()).isEqualTo("0sol")
        );
    }

    @Test
    @DisplayName("getSpaceMemberResponseList - 정상 조회")
    void getSpaceMemberResponseListTest() {
        Space space = spaceMemberList.get(0).getSpace();
        List<SpaceMemberResponse> spaceMemberResponseList = spaceMemberRepository
                .getSpaceMemberResponseList(space.getSpaceId());

        assertAll(
                () -> assertThat(spaceMemberResponseList.size()).isEqualTo(2),
                () -> assertThat(spaceMemberResponseList.get(0).getEmail())
                        .isEqualTo("0test@email.com"),
                () -> assertThat(spaceMemberResponseList.get(1).getEmail())
                        .isEqualTo("1test@email.com"));
    }

    @Test
    @DisplayName("existSpaceMember(memberId, spaceId) - 존재하지 않는 경우")
    void existSpaceMemberTestWhenNotExistWithMemberIdAndSpaceId() {
        Long memberId = 999L;
        Long spaceId = 999L;

        boolean exist = spaceMemberRepository.existSpaceMember(memberId, spaceId);

        assertThat(exist).isFalse();
    }

    @Test
    @DisplayName("existSpaceMember(memberId, spaceId) - 존재하는 경우")
    void existSpaceMemberTestWhenIsExistWithMemberIdAndSpaceId() {
        Member member = spaceMemberList.get(0).getMember();
        Space space = spaceMemberList.get(0).getSpace();

        boolean exist = spaceMemberRepository.existSpaceMember(
                member.getMemberId(),
                space.getSpaceId());

        assertThat(exist).isTrue();
    }

    @Test
    @DisplayName("existSpaceMember(spaceMemberId) - 존재하지 않는 경우")
    void existSpaceMemberTestWhenNotExistWithSpaceMemberId() {
        SpaceMember spaceMember = spaceMemberList.get(0);

        boolean exist = spaceMemberRepository.existSpaceMember(spaceMember.getSpaceMemberId());

        assertThat(exist).isTrue();
    }

    @Test
    @DisplayName("existSpaceMember(spaceMemberId) - 존재하는 경우")
    void existSpaceMemberTestWhenIsExistWithSpaceMemberId() {
        Long spaceMemberId = 999L;

        boolean exist = spaceMemberRepository.existSpaceMember(spaceMemberId);

        assertThat(exist).isFalse();
    }
}
