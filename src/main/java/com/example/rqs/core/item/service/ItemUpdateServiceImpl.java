package com.example.rqs.core.item.service;

import com.example.rqs.core.common.exception.BadRequestException;
import com.example.rqs.core.common.exception.ForbiddenException;
import com.example.rqs.core.common.exception.RQSError;
import com.example.rqs.core.item.Item;
import com.example.rqs.core.item.repository.ItemRepository;
import com.example.rqs.core.item.service.dtos.DeletedItemData;
import com.example.rqs.core.item.service.dtos.ItemResponse;
import com.example.rqs.core.item.service.dtos.UpdateItem;
import com.example.rqs.core.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemUpdateServiceImpl implements ItemUpdateService {

    private final ItemRepository itemRepository;
    private final ItemAuthService itemAuthService;

    @Override
    public ItemResponse updateItem(UpdateItem updateItem) {
        Item item = itemRepository.findById(updateItem.getItemId()).orElseThrow(BadRequestException::new);

        boolean isCreator = this.itemAuthService.isItemCreator(updateItem.getMember(), item);
        if (!isCreator) throw new ForbiddenException();

        item.updateContent(updateItem.getQuestion(), updateItem.getAnswer(), updateItem.getHint());
        return ItemResponse.of(item);
    }

    @Override
    public DeletedItemData deleteItem(Member member, Long itemId) {
        boolean isCreator = itemAuthService.isItemCreator(member, itemId);
        if (!isCreator) throw new ForbiddenException();

        Item item = itemRepository
                .findById(itemId)
                .orElseThrow(() -> new BadRequestException(RQSError.ITEM_IS_NOT_EXIST_IN_SPACE));

        List<Long> itemIds = itemRepository.getItemIds(item.getSpace().getSpaceId());
        int index = itemIds.indexOf(itemId);
        if (index == -1) throw new BadRequestException(RQSError.ITEM_IS_NOT_EXIST_IN_SPACE);

        itemRepository.deleteById(itemId);
        return DeletedItemData.of(item.getSpace().getSpaceId(), index);
    }
}
