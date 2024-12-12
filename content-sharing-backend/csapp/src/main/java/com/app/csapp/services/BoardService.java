package com.app.csapp.services;



import com.app.csapp.dtos.BoardDTO;
import com.app.csapp.exceptions.DataNotFoundException;
import com.app.csapp.models.Board;
import com.app.csapp.models.Picture;
import com.app.csapp.models.Tag;
import com.app.csapp.models.User;
import com.app.csapp.repositories.BoardRepository;
import com.app.csapp.repositories.PictureRepository;
import com.app.csapp.repositories.UserRepository;
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
    private final UserRepository userRepository;

    @Override
    public Board createBoard(BoardDTO boardDTO) throws DataNotFoundException {
        Picture existingPicture = pictureRepository.findById(boardDTO.getPictureId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find picture with ID: " + boardDTO.getPictureId()));
        User existingUser = userRepository.findById(boardDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find user with ID: " + boardDTO.getUserId()));

        Board newBoard = Board.
                builder()
                .user(existingUser)
                .boardName(boardDTO.getBoardName())
                .boardDescription(boardDTO.getBoardDescription())
                .picture(existingPicture)
                .build();
        return boardRespository.save(newBoard);

    }


    @Override
    public List<Board> getBoardOfUser(Long userId, String boardName) throws DataNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new DataNotFoundException("Cannot find user"));
        Board board = boardRespository.findBoardByName(boardName)
                .orElseThrow(()->new DataNotFoundException("Cannot find board with name: " + boardName));
        return boardRespository.findAllBoardOfUser(user.getId(), boardName);
    }

    @Override
    public int updateBoardName(Long userId, String boardName, BoardDTO boardDTO) throws DataNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new DataNotFoundException("Cannot find user"));
        Board board = boardRespository.findBoardByName(boardName)
                .orElseThrow(()->new DataNotFoundException("Cannot find board with name: " + boardName));
        return boardRespository.updateBoardName(userId, boardName, boardDTO.getBoardName());
    }

    @Override
    public void deletePictureOutOfBoard(Long userId,Long pictureId, String boardName) throws DataNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new  DataNotFoundException
                        ("Cannot find user with id: " + userId));
        Board board = boardRespository.findBoardByName(boardName)
                .orElseThrow(()->new DataNotFoundException("Cannot find board with name: " + boardName));
        Picture existingPicture = pictureRepository.findById(pictureId)
                .orElseThrow(()-> new DataNotFoundException
                        ("Cannot find picture with id: " + pictureId));
        boardRespository.deletePictureOfBoard(userId, pictureId, boardName);
    }

    @Override
    public void deleteBoardByUser(Long userId, String boardName ) throws DataNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new  DataNotFoundException
                        ("Cannot find user with id: " + userId));
        Board board = boardRespository.findBoardByName(boardName)
                .orElseThrow(()->new DataNotFoundException("Cannot find board with name: " + boardName));
        boardRespository.deleteBoardOfUser(userId, boardName);
    }
}
