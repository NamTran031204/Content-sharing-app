package com.app.csapp.services;

import com.app.csapp.dtos.ReactDTO;
import com.app.csapp.enums.ReactEnums;
import com.app.csapp.exceptions.DataNotFoundException;
import com.app.csapp.models.Picture;
import com.app.csapp.models.React;
import com.app.csapp.models.User;
import com.app.csapp.repositories.PictureRepository;
import com.app.csapp.repositories.ReactRepository;
import com.app.csapp.repositories.UserRepository;
import com.app.csapp.responses.ReactResponse;
import com.app.csapp.responses.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
            String shareLink = baseUrl + "/picture/" + existingPicture.getImageUrl();
            reactDTO.setContent(shareLink);
        }

        if(reactDTO.getTypeReact() == ReactEnums.LIKE){
            reactDTO.setContent("No content");
        }

        React newLike = React
                .builder()
                .user(existingUser)
                .picture(existingPicture)
                .typeReact(reactDTO.getTypeReact())
                .content(reactDTO.getContent())
                .build();


        return reactRepository.save(newLike);
    }

    @Override
    public React getAllReactById(ReactDTO reactDTO) throws DataNotFoundException {
        Long pictureId = pictureRepository.getPictureById(reactDTO.getPictureId())
                .orElseThrow(() -> new DataNotFoundException("khong ton tai anh"));
        //Long reactId = reactRepository.getIdByUserAndPictureAndTypeReact(User,)
        return null;
    }

    @Override
    public void deteleReact(ReactDTO reactDTO) throws DataNotFoundException {
        User existingUser = userRepository.findById(reactDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("set"));
        Picture existingPicture = pictureRepository.findById(reactDTO.getPictureId())
                .orElseThrow(() -> new DataNotFoundException("get"));
        Long reactId = reactRepository.getIdByUserAndPictureAndTypeReact(existingUser, existingPicture, reactDTO.getTypeReact());
        reactRepository.deleteById(reactId);
    }

    @Override
    public Page<UserResponse> getAllLike(long pictureId, PageRequest pageRequest) throws DataNotFoundException {
        Picture existingPicture = pictureRepository.findById(pictureId)
                .orElseThrow(()-> new DataNotFoundException("khong tim thay anh"));
        Page<UserResponse> userResponses = reactRepository.getUserByPictureAndTypeReact(existingPicture, ReactEnums.LIKE, pageRequest).map(user -> UserResponse.fromUser(user));
        return reactRepository.getUserByPictureAndTypeReact(existingPicture, ReactEnums.LIKE, pageRequest).map(user -> UserResponse.fromUser(user));
    }

//    private Page<React> syncFromIdtoReact(List<Long> id, PageRequest pageRequest ){
//        List<React> reactList = new ArrayList<>();
//        for(long x: id){
//            React exxistingReact = reactRepository.findById(x).orElseThrow(()-> new RuntimeException("khong tim thay"));
//            reactList.add(exxistingReact);
//        }
//        return new PageImpl<>(reactList, pageRequest, reactList.size());
//    }
    @Override
    public List<ReactResponse> getAllComment(long pictureId, PageRequest pageRequest) throws DataNotFoundException {
        Picture existingPicture = pictureRepository.findById(pictureId)
                .orElseThrow(()-> new DataNotFoundException("khong tim thay anh"));
        List<Long> reactList = reactRepository.getReactIdByPictureAndTypeReact(existingPicture, ReactEnums.COMMENT);
        List<ReactResponse> reactResponseList = new ArrayList<>();
        for (Long reactId: reactList){
            ReactResponse reactResponse = new ReactResponse();
            User user = reactRepository.getUserById(reactId);
            String content = reactRepository.getContentById(reactId);
            reactResponse.setUserName(user.getuserName());
            reactResponse.setName(user.getName());
            reactResponse.setProfilePicture(user.getProfilePicture());
            reactResponse.setContent(content);
            reactResponseList.add(reactResponse);
        }
        return reactResponseList;
    }




}
