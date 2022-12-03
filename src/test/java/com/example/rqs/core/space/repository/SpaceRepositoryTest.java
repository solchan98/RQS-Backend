package com.example.rqs.core.space.repository;

import com.example.rqs.core.config.DataTestConfig;
import com.example.rqs.core.item.Item;
import com.example.rqs.core.member.Member;
import com.example.rqs.core.member.repository.MemberRepository;
import com.example.rqs.core.space.Space;
import com.example.rqs.core.space.SpaceMember;
import com.example.rqs.core.space.SpaceRole;
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
    private LocalDateTime testCursorLocalDateTimeTheFirstMember; // 1번째 멤버의 테스트 스페이스 joinedAt
    private Long firstMemberId;

    @BeforeAll
    void init() {
        List<Space> testSpaceList = new ArrayList<>();
        List<Member> testMemberList = new ArrayList<>();

        // 초기 스페이스 및 스페이스 멤버 설정
        for (int idx = 0; idx < 5; idx++) {
            Member member = Member.newMember("member" + idx, "1234", "nickname" + idx);
            Space space = Space.newSpace("space" + idx, true);
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

        // 각 스페이스에 1 * (해당 스페이스 idx + 1) 만큼 퀴즈(아이템 추가
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
        spaceRepository.saveAll(testSpaceList);
    }

    @Test
    @DisplayName("getSpaceList - 정상 조회")
    void getSpaceListTest() {
        List<SpaceResponse> spaceList = spaceRepository.getSpaceList(null);

        assertAll(
                () -> assertThat(spaceList.size()).isEqualTo(5),
                () -> assertThat(spaceList.get(0).getTitle()).isEqualTo("space4"),
                () -> assertThat(spaceList.get(0).getSpaceMemberCount()).isEqualTo(2L),
                () -> assertThat(spaceList.get(0).getItemCount()).isEqualTo(5),
                () -> assertThat(spaceList.get(4).getTitle()).isEqualTo("space0"),
                () -> assertThat(spaceList.get(4).getSpaceMemberCount()).isEqualTo(1L),
                () -> assertThat(spaceList.get(4).getItemCount()).isEqualTo(1L)
        );
    }

    @Test
    @DisplayName("getSpaceList - 특정 기준 cursor 페이징")
    void getSpaceListWithCursorPagingTest() {
        List<SpaceResponse> spaceList = spaceRepository.getSpaceList(testCursorLocalDateTime);

        assertAll(
                () -> assertThat(spaceList.size()).isEqualTo(2),
                () -> assertThat(spaceList.get(0).getTitle()).isEqualTo("space1"),
                () -> assertThat(spaceList.get(0).getSpaceMemberCount()).isEqualTo(2L),
                () -> assertThat(spaceList.get(0).getItemCount()).isEqualTo(2),
                () -> assertThat(spaceList.get(1).getTitle()).isEqualTo("space0"),
                () -> assertThat(spaceList.get(1).getSpaceMemberCount()).isEqualTo(1L),
                () -> assertThat(spaceList.get(1).getItemCount()).isEqualTo(1)
        );
    }

    @Test
    @DisplayName("getSpaceListByTrending - 정상 조회")
    void getSpaceListByTrendingTest() {
        List<SpaceResponse> spaceListByTrending = spaceRepository.getSpaceListByTrending(0);

        assertAll(
                () -> assertThat(spaceListByTrending.get(0).getSpaceMemberCount()).isEqualTo(2L),
                () -> assertThat(spaceListByTrending.get(4).getSpaceMemberCount()).isEqualTo(1L)
        );
    }

    @Test
    @DisplayName("getSpaceListByTrending - 특정 기준 offset 페이징")
    void getSpaceListByTrendingWithOffsetPagingTest() {
        List<SpaceResponse> spaceListByTrending = spaceRepository.getSpaceListByTrending(3);

        assertAll(
                () -> assertThat(spaceListByTrending.size()).isEqualTo(2),
                () -> assertThat(spaceListByTrending.get(0).getSpaceMemberCount()).isEqualTo(2),
                () -> assertThat(spaceListByTrending.get(0).getItemCount()).isEqualTo(2),
                () -> assertThat(spaceListByTrending.get(1).getSpaceMemberCount()).isEqualTo(1),
                () -> assertThat(spaceListByTrending.get(1).getItemCount()).isEqualTo(1)
        );
    }

    @Test
    @DisplayName("getMySpaceList - 정상 조회")
    void getMySpaceListTest() {
        // firstMember =  첫번째 멤버 : 생성된 모든(5개) 테스트 스페이스에 참여된 상태

        List<SpaceResponse> mySpaceList = spaceRepository.getMySpaceList(firstMemberId, null);

        assertAll(
                () -> assertThat(mySpaceList.size()).isEqualTo(5L),
                () -> assertThat(mySpaceList.get(0).getSpaceMemberCount()).isEqualTo(2L),
                () -> assertThat(mySpaceList.get(0).getItemCount()).isEqualTo(5L),
                () -> assertThat(mySpaceList.get(0).getAuthority()).isEqualTo(SpaceRole.MEMBER),
                () -> assertThat(mySpaceList.get(4).getSpaceMemberCount()).isEqualTo(1L),
                () -> assertThat(mySpaceList.get(4).getItemCount()).isEqualTo(1L)
        );
    }

    @Test
    @DisplayName("getMySpaceList - 특정 기준 offset 페이징")
    void getMySpaceListWithOffsetPagingTest() {
        // firstMember =  첫번째 멤버 : 생성된 모든(5개) 테스트 스페이스에 참여된 상태

        List<SpaceResponse> mySpaceList = spaceRepository.getMySpaceList(firstMemberId, testCursorLocalDateTimeTheFirstMember);

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
}
