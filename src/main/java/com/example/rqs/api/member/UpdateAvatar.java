package com.example.rqs.api.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class UpdateAvatar {

    private MultipartFile image;
}
