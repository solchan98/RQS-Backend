package com.example.rqs.core.image;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @Id
    private String imageId;

    private String url;

    private Image(String imageId, String url) {
        this.imageId = imageId;
        this.url = url;
    }

    public static Image of(String imageName, String memberEmail, String url) {
        String imageId = memberEmail + "_" + imageName;
        return new Image(imageId, url);
    }
}
