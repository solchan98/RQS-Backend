package com.example.rqs.core.item.service;

import com.example.rqs.core.common.exception.ForbiddenException;
import com.example.rqs.core.item.Item;
import com.example.rqs.core.item.repository.ItemRepository;
import com.example.rqs.core.item.service.dtos.CreateItem;
import com.example.rqs.core.item.service.dtos.ItemResponse;
import com.example.rqs.core.spacemember.SpaceMember;
import com.example.rqs.core.spacemember.service.SpaceMemberAuthService;
import com.example.rqs.core.spacemember.service.SpaceMemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemRegisterServiceImpl implements ItemRegisterService {

    private final SpaceMemberReadService smReadService;
    private final SpaceMemberAuthService smAuthService;

    private final ItemRepository itemRepository;

    @Override
    public ItemResponse createItem(CreateItem createItem) {
        SpaceMember spaceMember = smReadService
                .getSpaceMember(createItem.getMember().getMemberId(), createItem.getSpaceId())
                .orElseThrow(ForbiddenException::new);

        boolean isCreatable = smAuthService.isCreatableItem(spaceMember);
        if(!isCreatable) throw new ForbiddenException();

        Item item = Item.newItem(spaceMember, createItem.getQuestion(), createItem.getAnswer(), createItem.getHint());
        itemRepository.save(item);
        return ItemResponse.of(item);
    }
}
