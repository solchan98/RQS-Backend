package com.example.rqs.core.item.service;

import com.example.rqs.core.item.Item;
import com.example.rqs.core.item.repository.ItemRepository;
import com.example.rqs.core.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemAuthServiceImpl implements ItemAuthService {

    private final ItemRepository itemRepository;

    @Override
    public boolean isItemCreator(Member member, Long itemId) {
        Optional<Item> itemOptional = itemRepository.findById(itemId);
        if (itemOptional.isEmpty()) return false;
        return this.isItemCreator(member, itemOptional.get());
    }

    @Override
    public boolean isItemCreator(Member member, Item itemId) {
        return itemId.getSpaceMember().getMember().getMemberId().equals(member.getMemberId());
    }
}
