package com.example.rqs.api.item;

import com.example.rqs.api.cache.randomItem.RandomItemCache;
import com.example.rqs.api.cache.randomItem.RandomItemCacheService;
import com.example.rqs.api.common.CommonAPIAuthChecker;
import com.example.rqs.api.exception.Message;
import com.example.rqs.api.jwt.MemberDetails;
import com.example.rqs.core.item.service.*;
import com.example.rqs.core.item.service.dtos.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ItemController {

    private static final String AUTH = "/my";
    private static final String DOMAIN = "/item";

    private final ItemReadService itemReadService;
    private final ItemRandomService itemRandomService;
    private final ItemAuthService itemAuthService;
    private final ItemUpdateService itemUpdateService;
    private final ItemRegisterService itemRegisterService;

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
        return itemRegisterService.createItem(createItem);
    }

    @GetMapping(DOMAIN)
    public ItemResponse getItem(
            HttpServletRequest request,
            @RequestParam("itemId") Long itemId
    ) {
        MemberDetails memberDetails = commonAPIAuthChecker.checkIsAuth(request.getHeader("Authorization"));
        return Objects.nonNull(memberDetails)
                ? itemReadService.getItem(ReadItem.of(memberDetails.getMember(), itemId))
                : itemReadService.getItem(ReadItem.of(itemId));
    }

    @GetMapping(DOMAIN + "/all")
    public List<ItemResponse> getItemList(
            HttpServletRequest request,
            @RequestParam("spaceId") Long spaceId,
            @Nullable @RequestParam("lastItemId") Long lastItemId
    ) {
        MemberDetails memberDetails = commonAPIAuthChecker.checkIsAuth(request.getHeader("Authorization"));
        return Objects.nonNull(memberDetails)
                ? itemReadService.getItemList(ReadItemList.of(memberDetails.getMember(), lastItemId, spaceId))
                : itemReadService.getItemList(ReadItemList.of(lastItemId, spaceId));
    }

    @GetMapping(AUTH + DOMAIN + "/random")
    public RandomItemResponse getRandomItem(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("spaceId") Long spaceId
    ) throws JsonProcessingException {
        Optional<RandomItemCache> randomItemCacheOp = randomItemCacheService
                .getCache(spaceId + "_" + memberDetails.getMember().getMemberId());
        boolean invalidCache = randomItemCacheOp.isEmpty() || randomItemCacheOp.get().getSelectableIndexList().size() == 0;

        ReadRandomItem readRandomItem = invalidCache
                ? ReadRandomItem.of(memberDetails.getMember(), spaceId)
                : ReadRandomItem.of(memberDetails.getMember(), spaceId, randomItemCacheOp.get().getSelectableIndexList());

        RandomItem randomItem = itemRandomService.getRandomItem(readRandomItem);
        String key = spaceId + "_" + memberDetails.getMember().getMemberId();
        RandomItemCache randomItemCache = randomItemCacheService.cache(key, randomItem, invalidCache);

        return RandomItemResponse.of(randomItemCache, randomItem.getItemResponse());
    }

    @GetMapping(AUTH + DOMAIN + "/creator")
    public Message checkIsCreator(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("itemId") Long itemId
    ) {
        boolean isItemCreator = itemAuthService.isItemCreator(memberDetails.getMember(), itemId);
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
        return itemUpdateService.updateItem(updateItem);
    }

    @DeleteMapping(AUTH + DOMAIN)
    public DeleteResponse deleteItem(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("itemId") Long itemId
    ) throws JsonProcessingException {
        DeletedItemData deletedItemData = itemUpdateService.deleteItem(memberDetails.getMember(), itemId);
        randomItemCacheService.deleteIndexInCache(deletedItemData.getSpaceId(), deletedItemData.getItemIndex());
        return DeleteResponse.of(itemId, true);
    }
}
