package com.example.rqs.api.space;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateSpaceDto {
    private Long spaceId;
    private String title;
}
