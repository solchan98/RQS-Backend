package com.example.rqs.api.item;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateItemDto {

    private Long spaceId;

    private String question;

    private String answer;

    private String hint;
}
