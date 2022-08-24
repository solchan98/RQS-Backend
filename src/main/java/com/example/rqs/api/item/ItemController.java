package com.example.rqs.api.item;

import com.example.rqs.api.jwt.MemberDetails;
import com.example.rqs.core.item.service.ItemService;
import com.example.rqs.core.item.service.dtos.*;

import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/item")
public class ItemController {

    private final ItemService itemService;
    private final CreateItemValidator createItemValidator;
    private final UpdateItemValidator updateItemValidator;

    @InitBinder("createItemDto")
    public void initCreateItemBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(createItemValidator);
    }

    @InitBinder("updateItemDto")
    public void initUpdateItemBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(updateItemValidator);
    }

    public ItemController(ItemService itemService, CreateItemValidator createItemValidator, UpdateItemValidator updateItemValidator) {
        this.itemService = itemService;
        this.createItemValidator = createItemValidator;
        this.updateItemValidator = updateItemValidator;
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
    public List<ItemResponse> getItemList(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("spaceId") Long spaceId,
            @Nullable @RequestParam("lastItemId") Long lastItemId
    ) {
        ReadItem readItem = ReadItem.of(
                memberDetails.getMember(),
                lastItemId,
                spaceId);
        return itemService.getItemList(readItem);
    }

    @GetMapping("/random")
    public ItemResponse getRandomItem(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("spaceId") Long spaceId
    ) {
        ReadItem readItem = ReadItem.of(
                memberDetails.getMember(),
                spaceId);
        return itemService.getRandomItem(readItem);
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
            @RequestParam("itemId") Long itemId
    ) {
        DeleteItem deleteItem = DeleteItem.of(memberDetails.getMember(), itemId);
        itemService.deleteItem(deleteItem);
        return DeleteResponse.of(itemId, true);
    }
}
