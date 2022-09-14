package com.example.rqs.core.item;

import com.example.rqs.core.common.exception.BadRequestException;
import com.example.rqs.core.common.exception.RQSError;
import com.example.rqs.core.item.repository.ItemRepository;
import com.example.rqs.core.item.service.ItemServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("아이템 서비스 테스트")
public class ItemServiceTest {

    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private ItemRepository itemRepository;

    @Test
    @DisplayName("아이템 인덱스 조회 시, 해당 아이템이 존재하지 않는 경우")
    void failGetItemIndexWhenItemIsNotExist() {
        Long spaceId = 1L; Long itemId = 1L;
        given(itemRepository.getItemIdList(spaceId)).willReturn(List.of());

        BadRequestException exception = assertThrows(BadRequestException.class, () -> itemService.getItemIndex(spaceId, itemId));

        assertThat(exception.getMessage()).isEqualTo(RQSError.ITEM_IS_NOT_EXIST_IN_SPACE);
    }
}
