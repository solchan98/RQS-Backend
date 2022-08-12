package com.example.rqs.core.space.service.dtos;

import com.example.rqs.core.member.Member;
import lombok.Getter;

@Getter
public class CreateSpace {

    private Member creator;

    private String title;

    private boolean visibility;
}
