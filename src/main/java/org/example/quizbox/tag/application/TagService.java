package org.example.quizbox.tag.application;

import lombok.RequiredArgsConstructor;
import org.example.quizbox.tag.domain.ITagRepository;
import org.example.quizbox.tag.domain.Tag;
import org.example.quizbox.tag.domain.Tags;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public Tags getOrCreateTags(Collection<String> tagNames) {
        Tags tags = tagRepository.findAllByNames(tagNames);

        Tags newTags = new Tags(tagNames.stream()
                .filter(tagName -> !tags.contains(tagName))
                .map(Tag::new)
                .collect(Collectors.toSet()));

        tagRepository.saveAll(newTags);
        tags.addAll(newTags);

        return tags;
    }

    @Transactional(readOnly = true)
    public Tag getOrCreateTag(String tagName) {
        return tagRepository.findByName(tagName)
                .orElseGet(() -> create(tagName));
    }

    @Transactional(propagation = Propagation.NESTED)
    public Tag create(String tagName) {
        Tag tag = new Tag(tagName);
        return tagRepository.save(tag);
    }

    @Transactional(propagation = Propagation.NESTED)
    public Tags createAll(Collection<String> tagNames) {
        return tagRepository.saveAll(
                new Tags(tagNames.stream()
                        .map(Tag::new)
                        .collect(Collectors.toSet())
                )
        );
    }
}
