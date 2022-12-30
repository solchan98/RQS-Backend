package com.example.rqs.core.item.service.dtos;

import com.example.rqs.core.member.Member;
import lombok.Getter;

import java.util.List;

@Getter
public class ReadRandomItem {

    private final Member member;

    private final Long spaceId;

    private final List<Long> selectableIndexList;

    private ReadRandomItem(Member member, Long spaceId, List<Long> selectableIndexList) {
        this.member = member;
        this.spaceId = spaceId;
        this.selectableIndexList = selectableIndexList;
    }

    public static ReadRandomItem of(Member member, Long spaceId) {
        return new ReadRandomItem(member, spaceId, List.of());
    }

    public static ReadRandomItem of(Member member, Long spaceId, List<Long> selectableIndexList) {
        return new ReadRandomItem(member, spaceId, selectableIndexList);
    }
}
