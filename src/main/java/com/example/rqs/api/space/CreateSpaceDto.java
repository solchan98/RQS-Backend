package com.example.rqs.api.space;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateSpaceDto {

    private String title;

    private boolean visibility;
}
