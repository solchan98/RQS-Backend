package org.example.quizbox.tag.presentation;

import lombok.RequiredArgsConstructor;
import org.example.quizbox.common.presentation.BasicResponse;
import org.example.quizbox.tag.application.TagService;
import org.example.quizbox.tag.domain.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping
    public ResponseEntity<BasicResponse<Tag>> create(@RequestBody CreateTagRequest createTagRequest) {
        Tag tag = tagService.create(createTagRequest.toGetOrCreateTagDto());

        return ResponseEntity.ok(new BasicResponse<>(tag));

    }
}
