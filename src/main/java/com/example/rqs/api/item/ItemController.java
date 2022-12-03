package com.example.rqs.api.item;

import com.example.rqs.api.cache.randomItem.RandomItemCache;
import com.example.rqs.api.cache.randomItem.RandomItemCacheService;
import com.example.rqs.api.common.CommonAPIAuthChecker;
import com.example.rqs.api.exception.Message;
import com.example.rqs.api.jwt.MemberDetails;
import com.example.rqs.core.item.service.ItemService;
import com.example.rqs.core.item.service.dtos.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ItemController {

    private static final String AUTH = "/my";
    private static final String DOMAIN = "/item";

    private final ItemService itemService;
    private final CreateItemValidator createItemValidator;
    private final UpdateItemValidator updateItemValidator;
    private final RandomItemCacheService randomItemCacheService;
    private final CommonAPIAuthChecker commonAPIAuthChecker;

    @InitBinder("createItemDto")
    public void initCreateItemBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(createItemValidator);
    }

    @InitBinder("updateItemDto")
    public void initUpdateItemBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(updateItemValidator);
    }

    public ItemController(ItemService itemService, CreateItemValidator createItemValidator, UpdateItemValidator updateItemValidator, RandomItemCacheService randomItemCacheService, CommonAPIAuthChecker commonAPIAuthChecker) {
        this.itemService = itemService;
        this.createItemValidator = createItemValidator;
        this.updateItemValidator = updateItemValidator;
        this.randomItemCacheService = randomItemCacheService;
        this.commonAPIAuthChecker = commonAPIAuthChecker;
    }

    @PostMapping(AUTH + DOMAIN)
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

    @GetMapping(DOMAIN)
    public ItemResponse getItem(
            HttpServletRequest request,
            @RequestParam("itemId") Long itemId
    ) {
        MemberDetails memberDetails = commonAPIAuthChecker.checkIsAuth(request.getHeader("Authorization"));
        return Objects.nonNull(memberDetails)
                ? itemService.getItem(ReadItem.of(memberDetails.getMember(), itemId))
                : itemService.getItem(ReadItem.of(itemId));
    }

    @GetMapping(DOMAIN + "/all")
    public List<ItemResponse> getItemList(
            HttpServletRequest request,
            @RequestParam("spaceId") Long spaceId,
            @Nullable @RequestParam("lastItemId") Long lastItemId
    ) {
        MemberDetails memberDetails = commonAPIAuthChecker.checkIsAuth(request.getHeader("Authorization"));
        return Objects.nonNull(memberDetails)
                ? itemService.getItemList(ReadItemList.of(memberDetails.getMember(), lastItemId, spaceId))
                : itemService.getItemList(ReadItemList.of(lastItemId, spaceId));
    }

    @GetMapping(AUTH + DOMAIN + "/random")
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

    @GetMapping(AUTH + DOMAIN + "/creator")
    public Message checkIsCreator(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("itemId") Long itemId
    ) {
        boolean isItemCreator = itemService.isItemCreator(memberDetails.getMember(), itemId);
        return isItemCreator
                ? new Message("200", HttpStatus.OK)
                : new Message("403", HttpStatus.FORBIDDEN);
    }

    @PutMapping(AUTH + DOMAIN)
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

    @DeleteMapping(AUTH + DOMAIN)
    public DeleteResponse deleteItem(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("itemId") Long itemId
    ) throws JsonProcessingException {
        DeleteItemCacheData deleteItemCacheData = itemService.getDeleteItemCacheData(itemId);
        randomItemCacheService.deleteIndexInCache(
                deleteItemCacheData.getSpaceId(),
                deleteItemCacheData.getItemIndex());
        DeleteItem deleteItem = DeleteItem.of(memberDetails.getMember(), itemId);
        itemService.deleteItem(deleteItem);
        return DeleteResponse.of(itemId, true);
    }
}
