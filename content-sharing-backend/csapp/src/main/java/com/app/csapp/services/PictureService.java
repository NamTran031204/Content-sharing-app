package com.app.csapp.services;

import com.app.csapp.dtos.PictureDTO;
import com.app.csapp.exceptions.DataNotFoundException;
import com.app.csapp.models.Picture;
import com.app.csapp.models.Tag;
import com.app.csapp.models.User;
import com.app.csapp.repositories.PictureRepository;
import com.app.csapp.repositories.TagRepository;
import com.app.csapp.repositories.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.Optional;

@Builder
@Service
@RequiredArgsConstructor
public class PictureService implements IPictureService {
    private final PictureRepository pictureRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    @Override
    public Picture createPicture(PictureDTO pictureDTO) throws DataNotFoundException {
        User existingUser = userRepository.findById(pictureDTO.getUserId()).
                orElseThrow(() -> new DataNotFoundException
                        ("Cannot find with user: " + pictureDTO.getUserId()));
        Picture newPicture = Picture.
                builder()
                .userId(existingUser)
                .imageDescription(pictureDTO.getImageDescription())
                .imageUrl(pictureDTO.getImageUrl())
                .title(pictureDTO.getTitle())
                .build();
        return pictureRepository.save(newPicture);
    }


    @Override
    public Picture getImageById(long id) throws Exception {
        return pictureRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot get the picture with the tag: " + id));
    }

    @Override
    public Page<Picture> getAllImage(PageRequest pageRequest) {
        //Lay danh sach trang anh theo page(so trang) va limit(cac phan tu trong 1 trang)
        return pictureRepository.findAll(pageRequest);
    }

    @Override
    public Picture updateImage(long imageId, PictureDTO pictureDTO) throws Exception {
        Picture exsitingPicture = getImageById(imageId);
        exsitingPicture.setImageUrl(pictureDTO.getImageUrl());
        exsitingPicture.setTitle(pictureDTO.getTitle());
        exsitingPicture.setImageDescription(pictureDTO.getImageDescription());
        return pictureRepository.save(exsitingPicture);
    }

    @Override
    public void deleteImage(long id) {
        Optional<Picture> optionalPicture = pictureRepository.findById(id);
        optionalPicture.ifPresent(pictureRepository::delete);

    }

    @Override
    public Picture createPictureUrl(Long pictureId, PictureDTO pictureDTO) throws DataNotFoundException {
//        Picture existingPicture = pictureRepository.findById(pictureDTO.getPictureTag()).
//                orElseThrow(() -> new DataNotFoundException
//                        ("Cannot find with tag: " + pictureDTO.getPictureTag()));
        Picture newPicture  = Picture
                .builder()
                .imageUrl(pictureDTO.getImageUrl())
                .build();
        return pictureRepository.save(newPicture);

    }
}

