package com.app.csapp.services;

import com.app.csapp.dtos.TagDTO;
import com.app.csapp.models.Tag;

import java.util.List;

public interface ITagService {
    Tag createTag(TagDTO tag);
    Tag getTagById (long id);
    List<Tag> getAllTagById();
    Tag updateTag(long tagId, TagDTO tag);
    void deleteTag(long id);
}
