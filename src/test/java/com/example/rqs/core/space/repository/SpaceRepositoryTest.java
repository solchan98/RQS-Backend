package com.example.rqs.core.space.repository;

import com.example.rqs.core.config.DataTestConfig;
import com.example.rqs.core.item.Item;
import com.example.rqs.core.member.Member;
import com.example.rqs.core.member.repository.MemberRepository;
import com.example.rqs.core.space.Space;
import com.example.rqs.core.spacemember.SpaceMember;
import com.example.rqs.core.spacemember.SpaceRole;
import com.example.rqs.core.space.service.dtos.SpaceResponse;
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
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("스페이스 레포지토리 테스트")
public class SpaceRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private SpaceRepository spaceRepository;

    private LocalDateTime testCursorLocalDateTime; // 3번째 스페이스의 createdAt
    private LocalDateTime testCursorLocalDateTimeTheFirstMember; // 첫번재 멤버가 세번째 스페이스에 참여한 시간
    private Long firstMemberId;
    private Long secondMemberId;

    @BeforeAll
    void init() {
        List<Space> testSpaceList = new ArrayList<>();
        List<Member> testMemberList = new ArrayList<>();

        // 초기 스페이스 및 스페이스 멤버 설정
        for (int idx = 0; idx < 5; idx++) {
            Member member = Member.newMember("member" + idx, "1234", "nickname" + idx);
            Space space = Space.newSpace("space" + idx, idx % 2 == 0); // idx 홀수 면 비공개, 짝수 면 공개 스페이스
            SpaceMember.newSpaceAdmin(member, space);
            testSpaceList.add(space);
            testMemberList.add(member);
            if (idx == 2) testCursorLocalDateTime = space.getCreatedAt();
        }

        // 2 ~ 5번째 스페이스에 1번째 멤버 추가
        for (int idx = 1; idx < testSpaceList.size(); idx++) {
            Member member = testMemberList.get(0);
            Space space = testSpaceList.get(idx);
            SpaceMember spaceMember = SpaceMember.newSpaceMember(member, space);
            if (idx == 2) testCursorLocalDateTimeTheFirstMember = spaceMember.getJoinedAt();
        }

        // 각 스페이스에 1 * (해당 스페이스 idx + 1) 만큼 퀴즈(아이템) 추가
        for (int idx = 0; idx < testSpaceList.size(); idx++) {
            Space space = testSpaceList.get(idx);
            SpaceMember spaceMember = space.getSpaceMemberList().get(0);
            List<Item> itemList = space.getItemList();
            for (int jdx = 0; jdx < idx + 1; jdx++) {
                Item item = Item.newItem(space, spaceMember, "Q" + jdx, "A" + jdx, "");
                itemList.add(item);
            }
        }
        memberRepository.saveAll(testMemberList);
        firstMemberId = testMemberList.get(0).getMemberId();
        secondMemberId = testMemberList.get(1).getMemberId();
        spaceRepository.saveAll(testSpaceList);
    }

    @Test
    @DisplayName("getSpaceList - 정상 조회")
    void getSpaceListTest() {
        List<SpaceResponse> spaceList = spaceRepository.getSpaceList(null);

        assertAll(
                () -> assertThat(spaceList.size()).isEqualTo(3),
                () -> assertThat(spaceList.get(0).getTitle()).isEqualTo("space4"),
                () -> assertThat(spaceList.get(0).getSpaceMemberCount()).isEqualTo(2L),
                () -> assertThat(spaceList.get(0).getItemCount()).isEqualTo(5),
                () -> assertThat(spaceList.get(2).getTitle()).isEqualTo("space0"),
                () -> assertThat(spaceList.get(2).getSpaceMemberCount()).isEqualTo(1L),
                () -> assertThat(spaceList.get(2).getItemCount()).isEqualTo(1L)
        );
    }

    @Test
    @DisplayName("getSpaceList - 특정 기준 cursor 페이징")
    void getSpaceListWithCursorPagingTest() {
        List<SpaceResponse> spaceList = spaceRepository.getSpaceList(testCursorLocalDateTime);

        assertAll(
                () -> assertThat(spaceList.size()).isEqualTo(1),
                () -> assertThat(spaceList.get(0).getTitle()).isEqualTo("space0"),
                () -> assertThat(spaceList.get(0).getSpaceMemberCount()).isEqualTo(1L),
                () -> assertThat(spaceList.get(0).getItemCount()).isEqualTo(1)
        );
    }

    @Test
    @DisplayName("getSpaceListByTrending - 정상 조회")
    void getSpaceListByTrendingTest() {
        List<SpaceResponse> spaceListByTrending = spaceRepository.getSpaceListByTrending(0L);

        assertAll(
                () -> assertThat(spaceListByTrending.get(0).getSpaceMemberCount()).isEqualTo(2L),
                () -> assertThat(spaceListByTrending.get(2).getSpaceMemberCount()).isEqualTo(1L)
        );
    }

    @Test
    @DisplayName("getSpaceListByTrending - 특정 기준 offset 페이징")
    void getSpaceListByTrendingWithOffsetPagingTest() {
        List<SpaceResponse> spaceListByTrending = spaceRepository.getSpaceListByTrending(2L);

        assertAll(
                () -> assertThat(spaceListByTrending.size()).isEqualTo(1),
                () -> assertThat(spaceListByTrending.get(0).getSpaceMemberCount()).isEqualTo(1),
                () -> assertThat(spaceListByTrending.get(0).getItemCount()).isEqualTo(1)
        );
    }

    @Test
    @DisplayName("getMembersSpaceList - memberId == targetMemberId")
    void getMySpaceListTest() {
        List<SpaceResponse> mySpaceList = spaceRepository.getMembersSpaceList(firstMemberId, firstMemberId, null);

        assertAll(
                () -> assertThat(mySpaceList.size()).isEqualTo(5L),
                () -> assertThat(mySpaceList.get(0).getSpaceMemberCount()).isEqualTo(2L),
                () -> assertThat(mySpaceList.get(0).getItemCount()).isEqualTo(5L),
                () -> assertThat(mySpaceList.get(0).getAuthority()).isEqualTo(SpaceRole.MEMBER),
                () -> assertThat(mySpaceList.get(4).getSpaceMemberCount()).isEqualTo(1L),
                () -> assertThat(mySpaceList.get(4).getItemCount()).isEqualTo(1L),
                () -> assertThat(mySpaceList.get(4).getAuthority()).isEqualTo(SpaceRole.ADMIN)
        );
    }

    @Test
    @DisplayName("getMembersSpaceList - 다른 사림이 조회")
    void getMySpaceListByAnotherTest() {
        List<SpaceResponse> mySpaceList = spaceRepository.getMembersSpaceList(secondMemberId, firstMemberId, null);

        assertAll(
                () -> assertThat(mySpaceList.size()).isEqualTo(3L),
                () -> assertThat(mySpaceList.get(0).getSpaceMemberCount()).isEqualTo(2L),
                () -> assertThat(mySpaceList.get(0).getItemCount()).isEqualTo(5L),
                () -> assertThat(mySpaceList.get(0).getAuthority()).isEqualTo(SpaceRole.MEMBER),
                () -> assertThat(mySpaceList.get(2).getSpaceMemberCount()).isEqualTo(1L),
                () -> assertThat(mySpaceList.get(2).getItemCount()).isEqualTo(1L),
                () -> assertThat(mySpaceList.get(2).getAuthority()).isEqualTo(SpaceRole.ADMIN)
        );
    }

    @Test
    @DisplayName("getMembersSpaceList - 게스트가 조회")
    void getMySpaceListByGuestTest() {
        List<SpaceResponse> mySpaceList = spaceRepository.getMembersSpaceList(null, firstMemberId, null);

        assertAll(
                () -> assertThat(mySpaceList.size()).isEqualTo(3L),
                () -> assertThat(mySpaceList.get(0).getSpaceMemberCount()).isEqualTo(2L),
                () -> assertThat(mySpaceList.get(0).getItemCount()).isEqualTo(5L),
                () -> assertThat(mySpaceList.get(0).getAuthority()).isEqualTo(SpaceRole.MEMBER),
                () -> assertThat(mySpaceList.get(2).getSpaceMemberCount()).isEqualTo(1L),
                () -> assertThat(mySpaceList.get(2).getItemCount()).isEqualTo(1L),
                () -> assertThat(mySpaceList.get(2).getAuthority()).isEqualTo(SpaceRole.ADMIN)
        );
    }

    @Test
    @DisplayName("getMySpaceList - (본인 타켓) 특정 기준 cursor 페이징")
    void getMySpaceListWithCursorPagingTest() {
        // firstMember =  첫번째 멤버 : 생성된 모든(5개) 테스트 스페이스에 참여된 상태
    // testCursorLocalDateTimeTheFirstMember = 첫번재 멤버가 세번째 스페이스에 참여한 시간

        List<SpaceResponse> mySpaceList = spaceRepository
                .getMembersSpaceList(firstMemberId, firstMemberId, testCursorLocalDateTimeTheFirstMember);

        assertAll(
                () -> assertThat(mySpaceList.size()).isEqualTo(2L),
                () -> assertThat(mySpaceList.get(0).getSpaceMemberCount()).isEqualTo(2L),
                () -> assertThat(mySpaceList.get(0).getItemCount()).isEqualTo(2L),
                () -> assertThat(mySpaceList.get(0).getAuthority()).isEqualTo(SpaceRole.MEMBER),
                () -> assertThat(mySpaceList.get(1).getSpaceMemberCount()).isEqualTo(1L),
                () -> assertThat(mySpaceList.get(1).getItemCount()).isEqualTo(1L),
                () -> assertThat(mySpaceList.get(1).getAuthority()).isEqualTo(SpaceRole.ADMIN)
        );
    }

    @Test
    @DisplayName("getMySpaceList - (다른 사람 타겟) 특정 기준 cursor 페이징")
    void getMySpaceListWithCursorPagingByAnotherTest() {
        // firstMember =  첫번째 멤버 : 생성된 모든(5개) 테스트 스페이스에 참여된 상태
        // testCursorLocalDateTimeTheFirstMember = 첫번재 멤버가 세번째 스페이스에 참여한 시간
        List<SpaceResponse> mySpaceList = spaceRepository
                .getMembersSpaceList(secondMemberId, firstMemberId, testCursorLocalDateTimeTheFirstMember);

        assertAll(
                () -> assertThat(mySpaceList.size()).isEqualTo(1L),
                () -> assertThat(mySpaceList.get(0).getSpaceMemberCount()).isEqualTo(1L),
                () -> assertThat(mySpaceList.get(0).getItemCount()).isEqualTo(1L),
                () -> assertThat(mySpaceList.get(0).getAuthority()).isEqualTo(SpaceRole.ADMIN)
        );
    }
}
