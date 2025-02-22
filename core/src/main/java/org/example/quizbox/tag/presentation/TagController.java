package org.example.quizbox.tag.presentation;

import lombok.RequiredArgsConstructor;
import org.example.quizbox.tag.application.TagService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

}
