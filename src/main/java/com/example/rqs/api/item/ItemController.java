package com.example.rqs.api.item;

import com.example.rqs.api.cache.quiz.QuizCacheService;
import com.example.rqs.api.common.CommonAPIAuthChecker;
import com.example.rqs.api.exception.Message;
import com.example.rqs.api.jwt.MemberDetails;
import com.example.rqs.core.item.service.*;
import com.example.rqs.core.item.service.dtos.*;

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

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ItemController {

    private static final String AUTH = "/my";
    private static final String DOMAIN = "/item";

    private final ItemReadService itemReadService;
    private final ItemAuthService itemAuthService;
    private final ItemUpdateService itemUpdateService;
    private final ItemRegisterService itemRegisterService;

    private final CreateItemValidator createItemValidator;
    private final UpdateItemValidator updateItemValidator;
    private final QuizCacheService quizCacheService;
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
        if (Objects.nonNull(memberDetails)) {
            return itemReadService.getItem(ReadItem.of(memberDetails.getMember(), itemId));
        }
        return itemReadService.getItem(ReadItem.of(itemId));
    }

    @GetMapping(DOMAIN + "/all")
    public List<ItemResponse> getItemList(
            HttpServletRequest request,
            @RequestParam("spaceId") Long spaceId,
            @Nullable @RequestParam("lastItemId") Long lastItemId
    ) {
        MemberDetails memberDetails = commonAPIAuthChecker.checkIsAuth(request.getHeader("Authorization"));
        if (Objects.nonNull(memberDetails)) {
            return itemReadService.getItems(ReadItemList.of(memberDetails.getMember(), lastItemId, spaceId));
        }

        return itemReadService.getItems(ReadItemList.of(lastItemId, spaceId));
    }

    @GetMapping(AUTH + DOMAIN + "/creator")
    public Message checkIsCreator(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("itemId") Long itemId
    ) {
        boolean isItemCreator = itemAuthService.isItemCreator(memberDetails.getMember(), itemId);
        if (isItemCreator) {
            return new Message("200", HttpStatus.OK);
        }

        return new Message("403", HttpStatus.FORBIDDEN);
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
    ) {
        DeletedItemData deletedItemData = itemUpdateService.deleteItem(memberDetails.getMember(), itemId);
        quizCacheService.deleteQuiz(deletedItemData.getSpaceId(), itemId);
        return DeleteResponse.of(itemId, true);
    }
}
