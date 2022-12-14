package com.example.rqs.core.item.service;

import com.example.rqs.core.common.exception.BadRequestException;
import com.example.rqs.core.common.exception.ForbiddenException;
import com.example.rqs.core.common.exception.RQSError;
import com.example.rqs.core.item.repository.ItemRepository;
import com.example.rqs.core.item.service.dtos.ItemResponse;
import com.example.rqs.core.item.service.dtos.RandomItem;
import com.example.rqs.core.item.service.dtos.ReadRandomItem;
import com.example.rqs.core.member.Member;
import com.example.rqs.core.space.Space;
import com.example.rqs.core.space.service.SpaceReadService;
import com.example.rqs.core.spacemember.service.SpaceMemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemRandomServiceImpl implements ItemRandomService {

    private final SpaceReadService spaceReadService;
    private final SpaceMemberReadService smReadService;

    private final ItemRepository itemRepository;

    public RandomItem getRandomItem(ReadRandomItem readRandomItem) {
        boolean isFirst = readRandomItem.getSelectableIndexList().size() == 0;
        return isFirst
                ? this.getRandomItem(readRandomItem.getMember(), readRandomItem.getSpaceId())
                : this.getRandomItem(readRandomItem.getMember(), readRandomItem.getSpaceId(), readRandomItem.getSelectableIndexList());
    }

    private RandomItem getRandomItem(Member member, Long spaceId) {
        Space space = spaceReadService.getSpace(spaceId).orElseThrow(BadRequestException::new);

        if (!space.isVisibility()) {
            smReadService.getSpaceMember(member.getMemberId(), spaceId).orElseThrow(ForbiddenException::new);
        }

        Long itemCnt = itemRepository.countBySpaceId(space.getSpaceId());
        if (itemCnt == 0) throw new BadRequestException(RQSError.SPACE_IS_EMPTY);

        int randomIdx = this.pickRandomIndex(itemCnt.intValue());
        ItemResponse itemResponse = itemRepository.getItem(spaceId, randomIdx);
        return RandomItem.of((long) randomIdx, itemResponse, itemCnt);
    }

    private RandomItem getRandomItem(Member member, Long spaceId, List<Long> selectableIndexList) {
        Space space = spaceReadService.getSpace(spaceId).orElseThrow(BadRequestException::new);

        if (!space.isVisibility()) {
            smReadService.getSpaceMember(member.getMemberId(), spaceId).orElseThrow(ForbiddenException::new);
        }

        int randomIdx = this.pickRandomIndex(selectableIndexList.size());
        ItemResponse itemResponse = itemRepository.getItem(spaceId, selectableIndexList.get(randomIdx).intValue());
        return RandomItem.of((long) randomIdx, itemResponse);
    }

    private int pickRandomIndex(int size) {
        Random random = new Random();
        return random.nextInt(size);
    }

}
