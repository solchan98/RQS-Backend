package com.example.rqs.core.item.service;

import com.example.rqs.core.common.exception.BadRequestException;
import com.example.rqs.core.common.exception.ForbiddenException;
import com.example.rqs.core.item.Item;
import com.example.rqs.core.item.repository.ItemRepository;
import com.example.rqs.core.item.service.dtos.ItemResponse;
import com.example.rqs.core.item.service.dtos.ReadItem;
import com.example.rqs.core.item.service.dtos.ReadItemList;
import com.example.rqs.core.member.Member;
import com.example.rqs.core.space.Space;
import com.example.rqs.core.space.service.SpaceReadService;
import com.example.rqs.core.spacemember.service.SpaceMemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemReadServiceImpl implements ItemReadService {

    private final SpaceReadService spaceReadService;
    private final SpaceMemberReadService smReadService;

    private final ItemRepository itemRepository;

    @Override
    public ItemResponse getItem(ReadItem readItem) {
        Item item = itemRepository.findById(readItem.getItemId()).orElseThrow(BadRequestException::new);

        boolean isGuest = Objects.isNull(readItem.getMember());
        if (!item.getSpace().isVisibility()) {
            if (isGuest) throw new ForbiddenException();
            this.checkIsSpaceMember(readItem.getMember(), item.getSpace().getSpaceId());
        }

        return ItemResponse.of(item);
    }

    @Override
    public List<ItemResponse> getItemList(ReadItemList readItemList) {
        Space space = spaceReadService
                .getSpace(readItemList.getSpaceId())
                .orElseThrow(BadRequestException::new);

        boolean isGuest = Objects.isNull(readItemList.getMember());
        if (!space.isVisibility()) {
            if (isGuest) throw new ForbiddenException();
            this.checkIsSpaceMember(readItemList.getMember(), space.getSpaceId());
        }

        return itemRepository.getItemList(readItemList.getSpaceId(), readItemList.getLastId());
    }

    private void checkIsSpaceMember(Member member, Long spaceId) throws ForbiddenException {
        smReadService.getSpaceMember(member.getMemberId(), spaceId).orElseThrow(ForbiddenException::new);
    }
}
