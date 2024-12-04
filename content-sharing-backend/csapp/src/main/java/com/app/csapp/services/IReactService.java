package com.app.csapp.services;

import com.app.csapp.dtos.ReactDTO;
import com.app.csapp.exceptions.DataNotFoundException;
import com.app.csapp.models.React;

import javax.xml.crypto.Data;
import java.util.List;

public interface IReactService {
    React createReact(ReactDTO reactDTO) throws DataNotFoundException;

//    React getBoardById (long id) throws DataNotFoundException;
//    List<React> getAllBoard();
//    React updateBoard(long reactId, ReactDTO reactDTO);
//    void deleteBoard(long id);
}
