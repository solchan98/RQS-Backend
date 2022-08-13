package com.example.rqs.core.space.service.dtos;

import com.example.rqs.core.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor // TODO: DELETE!
public class ReadSpace {

    private Member member;

    private Boolean visibility;
}
