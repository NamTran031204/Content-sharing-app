package com.app.csapp.services;



import com.app.csapp.dtos.BoardDTO;
import com.app.csapp.exceptions.DataNotFoundException;
import com.app.csapp.models.Board;
import com.app.csapp.models.Picture;
import com.app.csapp.repositories.BoardRepository;
import com.app.csapp.repositories.PictureRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Builder
@Service
@RequiredArgsConstructor
public class BoardService implements IBoardService {

    private final PictureRepository pictureRepository;
    private final BoardRepository boardRespository;

    @Override
    public Board createBoard(BoardDTO boardDTO) throws DataNotFoundException {
        Picture existingPicture = pictureRepository.findById(boardDTO.getPictureId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find picture with ID: " + boardDTO.getPictureId()));
        Board newBoard = Board.
                builder()
                .userId(boardDTO.getUserId())
                .boardName(boardDTO.getBoardName())
                .boardDescription(boardDTO.getBoardDescription())
                .pictureId(existingPicture)
                .build();
        return boardRespository.save(newBoard);

    }

    @Override
    public Board getBoardById(long id) throws DataNotFoundException {
        return boardRespository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot get the board with id: " + id));
    }

    @Override
    public List<Board> getAllBoard() {
        return boardRespository.findAll();
    }

    @Override
    public Board updateBoard(long boardId, BoardDTO board) {
        return null;
    }

    @Override
    public void deleteBoard(long id) {
        boardRespository.deleteById(id);
    }
}
