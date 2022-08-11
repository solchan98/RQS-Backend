package com.example.random_question_study.core.space;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Space {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long spaceId;

    private String title;

    private boolean visibility;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
