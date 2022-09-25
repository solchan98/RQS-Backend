package com.example.rqs.core.member.service.dtos;

import com.example.rqs.core.member.Member;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class UpdateAvatarDto {

    private final Member member;

    private final MultipartFile image;

    private UpdateAvatarDto(Member member, MultipartFile avatar) {
        this.member = member;
        this.image = avatar;
    }

    public static UpdateAvatarDto of(Member member, MultipartFile image) {
        return new UpdateAvatarDto(member, image);
    }
}
