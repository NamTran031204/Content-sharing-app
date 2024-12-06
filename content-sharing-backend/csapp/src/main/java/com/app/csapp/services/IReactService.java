package com.app.csapp.services;

import com.app.csapp.dtos.ReactDTO;
import com.app.csapp.exceptions.DataNotFoundException;
import com.app.csapp.models.React;

import javax.xml.crypto.Data;
import java.util.List;

public interface IReactService {
    React createReact(ReactDTO reactDTO) throws DataNotFoundException;
    React getAllReactById(ReactDTO reactDTO) throws DataNotFoundException;
    public void deteleReact(ReactDTO reactDTO) throws DataNotFoundException;
}
