package com.example.rqs.api.item;

import com.example.rqs.api.cache.randomItem.RandomItemCache;
import com.example.rqs.api.cache.randomItem.RandomItemCacheService;
import com.example.rqs.api.jwt.MemberDetails;
import com.example.rqs.core.common.exception.BadRequestException;
import com.example.rqs.core.item.service.ItemService;
import com.example.rqs.core.item.service.dtos.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/item")
public class ItemController {

    private final ItemService itemService;
    private final CreateItemValidator createItemValidator;
    private final UpdateItemValidator updateItemValidator;
    private final RandomItemCacheService randomItemCacheService;

    @InitBinder("createItemDto")
    public void initCreateItemBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(createItemValidator);
    }

    @InitBinder("updateItemDto")
    public void initUpdateItemBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(updateItemValidator);
    }

    public ItemController(ItemService itemService, CreateItemValidator createItemValidator, UpdateItemValidator updateItemValidator, RandomItemCacheService randomItemCacheService) {
        this.itemService = itemService;
        this.createItemValidator = createItemValidator;
        this.updateItemValidator = updateItemValidator;
        this.randomItemCacheService = randomItemCacheService;
    }

    @PostMapping("")
    public ItemResponse createNewItem(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody CreateItemDto createItemDto
    ) {
        CreateItem createItem = CreateItem.of(
                createItemDto.getSpaceId(),
                memberDetails.getMember(),
                createItemDto.getQuestion(),
                createItemDto.getAnswer(),
                createItemDto.getHint());
        return itemService.createNewItem(createItem);
    }

    @GetMapping("")
    public ItemResponse getItem(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("itemId") Long itemId
    ) {
        ReadItem readItem = ReadItem.of(memberDetails.getMember(), itemId);
        return itemService.getItem(readItem);
    }

    @GetMapping("/all")
    public List<ItemResponse> getItemList(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("spaceId") Long spaceId,
            @Nullable @RequestParam("lastItemId") Long lastItemId
    ) {
        ReadItemList readItemList = ReadItemList.of(
                memberDetails.getMember(),
                lastItemId,
                spaceId);
        return itemService.getItemList(readItemList);
    }

    @GetMapping("/random")
    public ItemResponse getRandomItem(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("spaceId") Long spaceId
    ) throws JsonProcessingException {
        Optional<RandomItemCache> randomItemCache = randomItemCacheService
                .getCache(spaceId + "_" + memberDetails.getMember().getMemberId());
        boolean invalidCache = randomItemCache.isEmpty() || randomItemCache.get().getSelectableIndexList().size() == 0;

        RandomItemResponse randomItemResponse;
        if (invalidCache) {
            randomItemResponse = itemService.getRandomItem(memberDetails.getMember(), spaceId);
            randomItemCacheService.addNewCache(
                    spaceId + "_" + memberDetails.getMember().getMemberId(),
                    randomItemResponse.getTotalCnt(),
                    randomItemResponse.getSelectedCacheListIndex().intValue());
        } else {
            RandomItemCache itemCache = randomItemCache.get();
            ReadRandomItem readRandomItem = ReadRandomItem.of(
                    memberDetails.getMember(), spaceId, itemCache.getSelectableIndexList());
            randomItemResponse = itemService.getRandomItem(readRandomItem);
            randomItemCacheService.updateCache(
                    spaceId + "_" + memberDetails.getMember().getMemberId(),
                    randomItemResponse.getSelectedCacheListIndex().intValue());
        }
        return randomItemResponse.getItemResponse();
    }

    @PutMapping("")
    public ItemResponse updateItem(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Validated @RequestBody UpdateItemDto updateItemDto
    ) {
        UpdateItem updateItem = UpdateItem.of(
                memberDetails.getMember(),
                updateItemDto.getItemId(),
                updateItemDto.getQuestion(),
                updateItemDto.getAnswer(),
                updateItemDto.getHint());
        return itemService.updateItem(updateItem);
    }

    @DeleteMapping("")
    public DeleteResponse deleteItem(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("spaceId") Long spaceId,
            @RequestParam("itemId") Long itemId
    ) {
        boolean exist = randomItemCacheService.existCacheByKeyPattern(spaceId + "_*");
        if (exist) throw new BadRequestException("현재 아이템을 삭제할 수 없습니다. 5분 뒤에 다시 시도해보세요.");
        DeleteItem deleteItem = DeleteItem.of(memberDetails.getMember(), itemId);
        itemService.deleteItem(deleteItem);
        return DeleteResponse.of(itemId, true);
    }
}
