package com.app.csapp.services;

import com.app.csapp.dtos.ReactDTO;
import com.app.csapp.exceptions.DataNotFoundException;
import com.app.csapp.models.React;
import com.app.csapp.models.User;
import com.app.csapp.responses.ReactResponse;
import com.app.csapp.responses.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IReactService {
    React createReact(ReactDTO reactDTO) throws DataNotFoundException;
    React getAllReactById(ReactDTO reactDTO) throws DataNotFoundException;
    public void deteleReact(ReactDTO reactDTO) throws DataNotFoundException;
    public Page<UserResponse> getAllLike(long pictureId, PageRequest pageRequest) throws DataNotFoundException;
    public List<ReactResponse>  getAllComment(long pictureId, PageRequest pageRequest) throws DataNotFoundException;
}
