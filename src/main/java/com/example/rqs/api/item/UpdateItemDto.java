package com.example.rqs.api.item;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateItemDto {

    private Long itemId;

    private String question;

    private String answer;

    private String hint;
}
