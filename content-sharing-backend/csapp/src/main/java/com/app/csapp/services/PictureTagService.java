package com.app.csapp.services;


import com.app.csapp.dtos.PictureTagDTO;
import com.app.csapp.dtos.TagDTO;
import com.app.csapp.exceptions.DataNotFoundException;
import com.app.csapp.models.Picture;
import com.app.csapp.models.PictureTag;
import com.app.csapp.models.Tag;
import com.app.csapp.repositories.PictureRepository;
import com.app.csapp.repositories.PictureTagRepository;
import com.app.csapp.repositories.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PictureTagService implements IPictureTagService {
    private final PictureRepository pictureRepository;
    private final TagRepository tagRepository;
    private final PictureTagRepository pictureTagRepository;

    @Override
    public List<PictureTag> createPictureTag(PictureTagDTO pictureTagDTO) throws DataNotFoundException {
        Picture existingPicture = pictureRepository.findById(pictureTagDTO.getPictureId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find picture with ID: " + pictureTagDTO.getPictureId()));

        List<Long> tagIds = Collections.singletonList(pictureTagDTO.getTagId());  // Assuming PictureTagDTO has a list of tag IDs
        List<PictureTag> pictureTags = new ArrayList<>();

        for (Long tagId : tagIds) {
            Tag tag = tagRepository.findById(tagId)
                    .orElseThrow(() -> new DataNotFoundException("Cannot find tag with ID: " + tagId));

            PictureTag newPictureTag = PictureTag.builder()
                    .pictureId(existingPicture)
                    .tagId(tag)
                    .build();

            pictureTagRepository.save(newPictureTag);
        }
        return pictureTags;
    }



    @Override
    public PictureTag getPictureTagById(long id) throws DataNotFoundException {
        return pictureTagRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot get the picture tag: " + id));
    }

    @Override
    public List<PictureTag> getAllPictureTag() {
        return pictureTagRepository.findAll();
    }

    @Override
    public PictureTag updatePictureTag(long tagId, PictureTagDTO pictureTagDTO) {
        return null;
    }

    @Override
    public void deletePictureTag(long id) {
        pictureRepository.deleteById(id);
    }
}
