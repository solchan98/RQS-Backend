package org.example.quizbox.tag.application;

import lombok.RequiredArgsConstructor;
import org.example.quizbox.common.BusinessException;
import org.example.quizbox.common.ExceptionConstants;
import org.example.quizbox.tag.domain.ITagRepository;
import org.example.quizbox.tag.domain.Tag;
import org.example.quizbox.tag.domain.Tags;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TagService {

    private final ITagRepository tagRepository;

    @Transactional(readOnly = true)
    public boolean existsAll(Set<Long> tagIds) {
        return tagIds.size() == tagRepository.findAllByIds(tagIds).size();
    }

    public Tags getAll(Collection<Long> tagIds) {
        return tagRepository.findAllByIds(tagIds);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Tag create(GetOrCreateTagDto createTagDto) {
        try {
            Tag newTag = new Tag(createTagDto.tagName());
            return tagRepository.save(newTag);
        } catch (DataIntegrityViolationException e) {
            return tagRepository.findByName(createTagDto.tagName())
                    .orElseThrow(() -> new BusinessException(ExceptionConstants.SE1));
        }
    }
}
