package com.app.csapp.services;

import com.app.csapp.dtos.BoardDTO;
import com.app.csapp.exceptions.DataNotFoundException;
import com.app.csapp.models.Board;

import java.util.List;

public interface IBoardService {
    Board createBoard(BoardDTO board) throws Exception;
    List<Board> getBoardOfUser(Long userId, String boardName) throws DataNotFoundException;
    int updateBoardName(Long userId ,String boardName, BoardDTO boardDTO) throws DataNotFoundException;
    void deleteBoardByUser(Long userId, String boardName ) throws DataNotFoundException;
    void deletePictureOutOfBoard(Long userId,Long pictureId, String boardName) throws DataNotFoundException;
}
