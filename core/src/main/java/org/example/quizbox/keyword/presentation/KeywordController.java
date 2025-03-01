package org.example.quizbox.keyword.presentation;

import lombok.RequiredArgsConstructor;
import org.example.quizbox.keyword.application.KeywordService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/keywords")
@RequiredArgsConstructor
public class KeywordController {

    private final KeywordService tagService;

}
