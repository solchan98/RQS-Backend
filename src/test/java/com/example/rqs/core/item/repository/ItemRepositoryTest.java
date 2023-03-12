package com.example.rqs.core.item.repository;

import com.example.rqs.core.config.DataTestConfig;
import com.example.rqs.core.item.Item;
import com.example.rqs.core.item.service.dtos.ItemResponse;
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
public class ItemRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ItemRepository itemRepository;

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
        List<Item> itemList = new ArrayList<>(30);
        for (int idx = 0; idx < 30; idx++) {
            Item item = Item.newItem(spaceMember, "Question_" + idx, "Answer", "");
            itemList.add(item);
        }
        itemRepository.saveAll(itemList);
    }

    @BeforeAll
    void init() {
        setUpSpaceMember();
        createItems(spaceMember);
    }

    @AfterAll
    void clear() {
        itemRepository.deleteAllInBatch();
        spaceMemberRepository.deleteAllInBatch();
        spaceRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("getItemList - 첫 조회 시")
    void getItemListTestWhenFirstRead() {
        Space space = spaceMember.getSpace();

        List<ItemResponse> items = itemRepository.getItems(space.getSpaceId(), null);

        assertThat(items.size()).isEqualTo(20);
    }

    @Test
    @DisplayName("getItemList - 두 번째 조회")
    void getItemListTestWhenSecondRead() {
        Space space = spaceMember.getSpace();

        List<ItemResponse> firstRead = itemRepository.getItems(space.getSpaceId(), null);
        Long lastId = firstRead.get(firstRead.size() - 1).getItemId();
        List<ItemResponse> items = itemRepository.getItems(space.getSpaceId(), lastId);

        assertThat(items.size()).isEqualTo(10);
    }

    @Test
    @DisplayName("countBySpaceId - 정상 조회")
    void countBySpaceIdTest() {
        Space space = spaceMember.getSpace();
        // init()을 통해 최초로 Item 30개를 생성 (동일 스페이스)

        Long itemCount = itemRepository.countBySpaceId(space.getSpaceId());

        assertThat(itemCount).isEqualTo(30L);
    }

    @Test
    @DisplayName("countBySpaceId - 존재하지 않는 스페이스인 경우")
    void countBySpaceIdTestWhenIsEmpty() {

        Long itemCount = itemRepository.countBySpaceId(999L);

        assertThat(itemCount).isEqualTo(0L);
    }

    @Test
    @DisplayName("getItem(spaceId) - 정상 조회")
    void getItemBySpaceIdAndRandomIdxTest() {
        Space space = spaceMember.getSpace();
        List<Long> itemIds = itemRepository.getItemIds(space.getSpaceId());

        ItemResponse itemResponse = itemRepository.getItem(itemIds.get(0));

        assertThat(itemResponse).isNotNull();
    }

    @Test
    @DisplayName("getItemIdList(spaceId) - 정상 조회")
    void getItemIdListTest() {
        Space space = spaceMember.getSpace();

        List<Long> itemIds = itemRepository.getItemIds(space.getSpaceId());

        assertThat(itemIds.size()).isEqualTo(30L);
    }

}
