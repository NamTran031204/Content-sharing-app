package com.app.csapp.services;

import com.app.csapp.dtos.ReactDTO;
import com.app.csapp.enums.ReactEnums;
import com.app.csapp.exceptions.DataNotFoundException;
import com.app.csapp.models.Picture;
import com.app.csapp.models.React;
import com.app.csapp.repositories.PictureRepository;
import com.app.csapp.repositories.ReactRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReactService implements IReactService{
    private final PictureRepository pictureRepository;
    private final ReactRepository reactRepository;

    @Value("${app.base-url}")
    private String baseUrl;

    @Override
    public React createReact(ReactDTO reactDTO) throws DataNotFoundException {
        Picture existingPicture = pictureRepository.findById(reactDTO.getPictureId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find picture with ID: " + reactDTO.getPictureId()));
        if((reactDTO.getTypeReact() == ReactEnums.COMMENT) &&
            (reactDTO.getContent() == null || reactDTO.getContent().isBlank())){
                throw new IllegalArgumentException("Content cannot be null when comment or share");
        }

        if(reactDTO.getTypeReact() == ReactEnums.SHARE){
            String shareLink = baseUrl + "/picture/" + reactDTO.getPictureId();
            reactDTO.setContent(shareLink);
        }

        if(reactDTO.getTypeReact() == ReactEnums.LIKE){
            reactDTO.setContent("No content");
        }

        React newLike = React
                .builder()
                .userId(reactDTO.getUserId())
                .pictureId(existingPicture)
                .typeReact(reactDTO.getTypeReact())
                .content(reactDTO.getContent())
                .build();


        return reactRepository.save(newLike);
    }




//    @Override
//    public React getBoardById(long id) throws DataNotFoundException {
//        return null;
//    }
//
//    @Override
//    public List<React> getAllBoard() {
//        return List.of();
//    }
//
//    @Override
//    public React updateBoard(long reactId, ReactDTO reactDTO) {
//        return null;
//    }
//
//    @Override
//    public void deleteBoard(long id) {
//
//    }
}
