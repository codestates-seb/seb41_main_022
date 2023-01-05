package codestates.main22.hashtag.service;

import codestates.main22.hashtag.repository.HashtagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HashtagService {
    private HashtagRepository hashtagRepository;
}