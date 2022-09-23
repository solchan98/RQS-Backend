package com.example.rqs.core.Image;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
public class Image {

    @Id
    private String imageId; // memberId + filename

    private String name;

    private String type; // TODO: Type을 지정한다면 Enum으로 변경
}
