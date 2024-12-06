package com.app.csapp.services;

import com.app.csapp.dtos.PictureDTO;
import com.app.csapp.dtos.ReactDTO;
import com.app.csapp.dtos.UserDTO;
import com.app.csapp.enums.ReactEnums;
import com.app.csapp.exceptions.DataNotFoundException;
import com.app.csapp.models.Picture;
import com.app.csapp.models.React;
import com.app.csapp.models.User;
import com.app.csapp.repositories.PictureRepository;
import com.app.csapp.repositories.ReactRepository;
import com.app.csapp.repositories.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReactService implements IReactService{
    private final PictureRepository pictureRepository;
    private final ReactRepository reactRepository;
    private final UserRepository userRepository;

    @Value("${app.base-url}")
    private String baseUrl;

    @Override
    public React createReact(ReactDTO reactDTO) throws DataNotFoundException {
        Picture existingPicture = pictureRepository.findById(reactDTO.getPictureId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find picture with ID: " + reactDTO.getPictureId()));
        User existingUser = userRepository.findById(reactDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("khong tim thay user with id: " + reactDTO.getUserId()));
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

        React newReact = React
                .builder()
                .user(existingUser)
                .picture(existingPicture)
                .typeReact(reactDTO.getTypeReact())
                .content(reactDTO.getContent())
                .build();

        return reactRepository.save(newReact);
    }

    @Override
    public React getAllReactById(ReactDTO reactDTO) throws DataNotFoundException {
        Long pictureId = pictureRepository.getPictureById(reactDTO.getPictureId())
                .orElseThrow(() -> new DataNotFoundException("khong ton tai anh"));
        //Long reactId = reactRepository.getIdByUserAndPictureAndTypeReact(User,)
        return null;
    }

    @Override
    public void deteleReact(ReactDTO reactDTO) throws DataNotFoundException{
        User existingUser = userRepository.findById(reactDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("set"));
        Picture existingPicture = pictureRepository.findById(reactDTO.getPictureId())
                .orElseThrow(() -> new DataNotFoundException("get"));

        Long reactId = reactRepository.getIdByUserAndPictureAndTypeReact(existingUser, existingPicture, reactDTO.getTypeReact());
        reactRepository.deleteById(reactId);
    }


}
