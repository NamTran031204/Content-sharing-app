package com.app.csapp.services;

import com.app.csapp.dtos.TagDTO;
import com.app.csapp.models.Tag;
import com.app.csapp.repositories.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class TagService implements ITagService{
    private final TagRepository tagRepository;

    @Override
    public Tag createTag(TagDTO tagDTO) {
        Tag newTag = Tag.builder()
                .tagName(tagDTO.getTagName()).build();
        return tagRepository.save(newTag);
    }

    @Override
    public Tag getTagById(long id) {
        return tagRepository.findById(id).orElseThrow(() -> new RuntimeException("Tag not found"));
    }

    @Override
    public List<Tag> getAllTagById() {
        return tagRepository.findAll();
    }

    @Override
    public Tag updateTag(long tagId, TagDTO tagDTO) {
        Tag existingTag = getTagById(tagId);
        existingTag.setTagName(tagDTO.getTagName());
        tagRepository.save(existingTag);
        return existingTag;
    }

    @Override
    public void deleteTag(long id) {
        //Xoa mat luon
        tagRepository.deleteById(id);
    }
}
