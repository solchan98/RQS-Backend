package com.example.rqs.core.item.service;

import com.example.rqs.core.common.exception.*;
import com.example.rqs.core.item.Item;
import com.example.rqs.core.item.repository.ItemRepository;
import com.example.rqs.core.item.service.dtos.*;
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
    public ItemResponse getRandomItem(ReadItem readItem) {
        Space space = spaceRepository
                .findById(readItem.getSpaceId())
                .orElseThrow(BadRequestException::new);
        Long itemCnt = itemRepository.countBySpaceId(space.getSpaceId());
        Random random = new Random();
        int randomIndex = random.nextInt(itemCnt.intValue());
        return space.isVisibility()
                ? this.getPublicSpaceRandomItem(readItem, randomIndex)
                : this.getPrivateSpaceRandomItem(readItem, randomIndex);
    }

    private ItemResponse getPublicSpaceRandomItem(ReadItem readItem, int randomIndex) {
        return itemRepository.getItem(readItem.getSpaceId(), randomIndex);
    }

    private ItemResponse getPrivateSpaceRandomItem(ReadItem readItem, int randomIndex) {
        boolean exist = spaceMemberRepository
                .existSpaceMember(readItem.getMember().getMemberId(), readItem.getSpaceId());
        if (!exist) throw new ForbiddenException();
        return this.getPublicSpaceRandomItem(readItem, randomIndex);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemResponse> getItemList(ReadItem readItem) {
        Space space = spaceRepository
                .findById(readItem.getSpaceId())
                .orElseThrow(BadRequestException::new);
        return space.isVisibility()
                ? this.getPublicSpaceItemList(readItem)
                : this.getPrivateSpaceItemList(readItem);
    }

    private List<ItemResponse> getPublicSpaceItemList(ReadItem readItem) {
        return itemRepository.getItemList(readItem.getSpaceId(), readItem.getLastId());
    }

    private List<ItemResponse> getPrivateSpaceItemList(ReadItem readItem) {
        boolean exist = spaceMemberRepository
                .existSpaceMember(readItem.getMember().getMemberId(), readItem.getSpaceId());
        if (!exist) throw new ForbiddenException();
        return this.getPublicSpaceItemList(readItem);
    }

    @Override
    public void updateQuestion() {

    }

    @Override
    public void updateAnswer() {

    }

    @Override
    public void updateHint() {

    }

    @Override
    public void deleteItem() {

    }
}
