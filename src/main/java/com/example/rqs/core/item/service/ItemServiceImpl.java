package com.example.rqs.core.item.service;

import com.example.rqs.core.common.exception.*;
import com.example.rqs.core.item.Item;
import com.example.rqs.core.item.repository.ItemRepository;
import com.example.rqs.core.item.service.dtos.*;
import com.example.rqs.core.member.Member;
import com.example.rqs.core.space.*;
import com.example.rqs.core.space.repository.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final SpaceRepository spaceRepository;
    private final SpaceMemberRepository spaceMemberRepository;

    public ItemServiceImpl(ItemRepository itemRepository, SpaceRepository spaceRepository, SpaceMemberRepository spaceMemberRepository) {
        this.itemRepository = itemRepository;
        this.spaceRepository = spaceRepository;
        this.spaceMemberRepository = spaceMemberRepository;
    }

    @Override
    @Transactional
    public ItemResponse createNewItem(CreateItem createItem) {
        SpaceMember spaceMember = spaceMemberRepository
                .getSpaceMember(createItem.getMember().getMemberId(), createItem.getSpaceId())
                .orElseThrow(BadRequestException::new);
        boolean isCreatable = spaceMember.isCreatableItem();
        if(!isCreatable) throw new ForbiddenException();
        Item item = Item.newItem(
                spaceMember.getSpace(),
                spaceMember,
                createItem.getQuestion(),
                createItem.getAnswer(),
                createItem.getHint());
        itemRepository.save(item);
        return ItemResponse.of(item);
    }

    @Override
    @Transactional(readOnly = true)
    public RandomItemResponse getRandomItem(ReadRandomItem readRandomItem) {
        Space space = spaceRepository
                .findById(readRandomItem.getSpaceId())
                .orElseThrow(BadRequestException::new);

        if (!space.isVisibility()) {
            boolean exist = spaceMemberRepository
                    .existSpaceMember(readRandomItem.getMember().getMemberId(), readRandomItem.getSpaceId());
            if (!exist) throw new ForbiddenException();
        }

        Random random = new Random();
        int randomIndex = random.nextInt(readRandomItem.getSelectableIndexList().size());
        Long randomItemIndex = readRandomItem.getSelectableIndexList().get(randomIndex);

        ItemResponse itemResponse = itemRepository.getItem(
                readRandomItem.getSpaceId(),
                randomItemIndex.intValue());

        return RandomItemResponse.of(
                (long) randomIndex,
                itemResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public RandomItemResponse getRandomItem(Member member, Long spaceId) {
        Space space = spaceRepository
                .findById(spaceId)
                .orElseThrow(BadRequestException::new);

        if (!space.isVisibility()) {
            boolean exist = spaceMemberRepository
                    .existSpaceMember(member.getMemberId(), spaceId);
            if (!exist) throw new ForbiddenException();
        }

        Long itemCnt = itemRepository.countBySpaceId(space.getSpaceId());
        if (itemCnt == 0) throw new BadRequestException(RQSError.SPACE_IS_EMPTY);

        Random random = new Random();
        int randomIndex = random.nextInt(itemCnt.intValue());
        ItemResponse itemResponse = itemRepository.getItem(spaceId, randomIndex);

        return RandomItemResponse.of((long) randomIndex, itemResponse, itemCnt);
    }

    @Override
    @Transactional(readOnly = true)
    public ItemResponse getItem(ReadItem readItem) {
        Item item = itemRepository
                .findById(readItem.getItemId())
                .orElseThrow(BadRequestException::new);
        if (!item.getSpace().isVisibility()) {
            boolean exist = spaceMemberRepository
                    .existSpaceMember(readItem.getMember().getMemberId(), item.getSpace().getSpaceId());
            if (!exist) throw new ForbiddenException();
        }
        return ItemResponse.of(item);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemResponse> getItemList(ReadItemList readItemList) {
        Space space = spaceRepository
                .findById(readItemList.getSpaceId())
                .orElseThrow(BadRequestException::new);
        if (!space.isVisibility()) {
            boolean exist = spaceMemberRepository
                    .existSpaceMember(readItemList.getMember().getMemberId(), readItemList.getSpaceId());
            if (!exist) throw new ForbiddenException();
        }
        return itemRepository.getItemList(readItemList.getSpaceId(), readItemList.getLastId());
    }

    @Override
    @Transactional
    public ItemResponse updateItem(UpdateItem updateItem) {
        Item item = itemRepository
                .findById(updateItem.getItemId())
                .orElseThrow(BadRequestException::new);
        boolean isCreator = isItemCreator(updateItem.getMember(), item);
        if (!isCreator) throw new ForbiddenException();
        item.updateContent(
                updateItem.getQuestion(),
                updateItem.getAnswer(),
                updateItem.getHint());
        return ItemResponse.of(item);
    }

    @Override
    @Transactional
    public void deleteItem(DeleteItem deleteItem) {
        Item item = itemRepository
                .findById(deleteItem.getItemId())
                .orElseThrow(BadRequestException::new);
        boolean isCreator = isItemCreator(deleteItem.getMember(), item);
        if (!isCreator) throw new ForbiddenException();
        itemRepository.delete(item);
    }

    @Override
    public int getItemIndex(Long spaceId, Long itemId) {
        List<Long> itemIdList = itemRepository
                .getItemIdList(spaceId);
        int index = itemIdList.indexOf(itemId);
        if (index == -1) throw new BadRequestException(RQSError.ITEM_IS_NOT_EXIST_IN_SPACE);
        return index;
    }

    private boolean isItemCreator(Member member, Item item) {
        SpaceMember itemCreator = spaceMemberRepository
                .getSpaceMember(member.getMemberId(), item.getSpace().getSpaceId())
                .orElseThrow(BadRequestException::new);
        return item.isCreator(itemCreator);
    }
}
