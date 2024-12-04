package com.app.csapp.services;

import com.app.csapp.dtos.BoardDTO;
import com.app.csapp.exceptions.DataNotFoundException;
import com.app.csapp.models.Board;

import java.util.List;

public interface IBoardService {
    Board createBoard(BoardDTO board) throws DataNotFoundException;
    Board getBoardById (long id) throws DataNotFoundException;
    List<Board> getAllBoard();
    Board updateBoard(long boardId, BoardDTO board);
    void deleteBoard(long id);
}
