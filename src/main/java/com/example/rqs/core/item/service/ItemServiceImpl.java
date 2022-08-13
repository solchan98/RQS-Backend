package com.example.rqs.core.item.service;

import com.example.rqs.core.common.exception.*;
import com.example.rqs.core.item.Item;
import com.example.rqs.core.item.repository.ItemRepository;
import com.example.rqs.core.item.service.dtos.*;
import com.example.rqs.core.space.SpaceMember;
import com.example.rqs.core.space.repository.SpaceMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final SpaceMemberRepository spaceMemberRepository;

    public ItemServiceImpl(ItemRepository itemRepository, SpaceMemberRepository spaceMemberRepository) {
        this.itemRepository = itemRepository;
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
