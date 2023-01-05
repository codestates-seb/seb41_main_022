package codestates.main22.hashtag.controller;

import codestates.main22.hashtag.mapper.HashtagMapper;
import codestates.main22.hashtag.service.HashtagService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hashtag")
@Validated
@AllArgsConstructor
public class HashtagController {
    private final HashtagService hashtagService;
    private final HashtagMapper hashtagMapper;
}
