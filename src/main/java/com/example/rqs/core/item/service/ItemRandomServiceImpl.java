package com.example.rqs.core.item.service;

import com.example.rqs.core.common.exception.BadRequestException;
import com.example.rqs.core.common.exception.ForbiddenException;
import com.example.rqs.core.common.exception.RQSError;
import com.example.rqs.core.item.repository.ItemRepository;
import com.example.rqs.core.item.service.dtos.ItemResponse;
import com.example.rqs.core.item.service.dtos.RandomItemResponse;
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

    @Override
    public RandomItemResponse getRandomItem(Member member, Long spaceId) {
        Space space = spaceReadService.getSpace(spaceId).orElseThrow(BadRequestException::new);

        if (!space.isVisibility()) {
            smReadService.getSpaceMember(member.getMemberId(), spaceId).orElseThrow(ForbiddenException::new);
        }

        Long itemCnt = itemRepository.countBySpaceId(space.getSpaceId());
        if (itemCnt == 0) throw new BadRequestException(RQSError.SPACE_IS_EMPTY);

        int randomIdx = this.pickRandomIndex(itemCnt.intValue());
        ItemResponse itemResponse = itemRepository.getItem(spaceId, randomIdx);
        return RandomItemResponse.of((long) randomIdx, itemResponse, itemCnt);
    }

    @Override
    public RandomItemResponse getRandomItem(ReadRandomItem readRandomItem) {
        Space space = spaceReadService.getSpace(readRandomItem.getSpaceId()).orElseThrow(BadRequestException::new);

        if (!space.isVisibility()) {
            smReadService
                    .getSpaceMember(readRandomItem.getMember().getMemberId(), readRandomItem.getSpaceId())
                    .orElseThrow(ForbiddenException::new);
        }

        int randomIdx = this.pickRandomIndex(readRandomItem.getSelectableIndexList());
        ItemResponse itemResponse = itemRepository.getItem(readRandomItem.getSpaceId(), randomIdx);
        return RandomItemResponse.of((long) randomIdx, itemResponse);
    }

    private int pickRandomIndex(int size) {
        Random random = new Random();
        return random.nextInt(size);
    }

    private int pickRandomIndex(List<Long> selectableIdxList) {
        Random random = new Random();
        int randomIdx = random.nextInt(selectableIdxList.size());
        return selectableIdxList.get(randomIdx).intValue();
    }
}
