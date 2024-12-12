package com.app.csapp.services;

import com.app.csapp.dtos.PictureTagDTO;
import com.app.csapp.exceptions.DataNotFoundException;
import com.app.csapp.models.PictureTag;

import java.util.List;

public interface IPictureTagService {
    List<PictureTag> createPictureTag(PictureTagDTO pictureTagDTO) throws DataNotFoundException;
    PictureTag getPictureTagById (long id) throws DataNotFoundException;
    List<PictureTag> getAllPictureTag(Long id) throws DataNotFoundException;
    void deleteAllPictureTagofPicture(Long pictureId) throws DataNotFoundException;
    void deleteTagWithPicture(Long pictureId, Long tagId) throws DataNotFoundException;
}
