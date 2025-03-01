package org.example.quizbox.keyword.application;

import lombok.RequiredArgsConstructor;
import org.example.quizbox.keyword.domain.IKeywordRepository;
import org.example.quizbox.keyword.domain.Keyword;
import org.example.quizbox.keyword.domain.Keywords;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class KeywordService {

    private final IKeywordRepository keywordRepository;

    @Transactional(readOnly = true)
    public boolean existsAll(Set<Long> tagIds) {
        return tagIds.size() == keywordRepository.findAllByIds(tagIds).size();
    }

    public Keywords getAll(Collection<Long> ids) {
        return keywordRepository.findAllByIds(ids);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Keyword save(Keyword keyword) {
        return keywordRepository.save(keyword);
    }
}
