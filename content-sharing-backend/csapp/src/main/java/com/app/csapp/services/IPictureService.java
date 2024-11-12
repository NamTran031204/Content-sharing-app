package com.app.csapp.services;

import com.app.csapp.dtos.PictureDTO;
import com.app.csapp.exceptions.DataNotFoundException;
import com.app.csapp.models.Picture;
import org.springframework.data.domain.*;
import java.awt.*;


public interface IPictureService {
    Picture createPicture(PictureDTO pictureDTO) throws DataNotFoundException;
    Picture getImageById (long id) throws DataNotFoundException, Exception;
    Page<Picture> getAllImage(PageRequest pageRequest);
    Picture updateImage(long imageId, PictureDTO pictureDTO) throws Exception;
    void deleteImage(long id);
    Picture createPictureUrl(Long pictureId, PictureDTO pictureDTO) throws DataNotFoundException;

}
