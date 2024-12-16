package com.app.csapp.services;



import com.app.csapp.dtos.BoardDTO;
import com.app.csapp.exceptions.DataNotFoundException;
import com.app.csapp.exceptions.SameDataException;
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
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Override
    public Board createBoard(BoardDTO boardDTO) throws DataNotFoundException, SameDataException {
        Picture existingPicture = pictureRepository.findById(boardDTO.getPictureId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find picture with ID: " + boardDTO.getPictureId()));
        User existingUser = userRepository.findById(boardDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find user with ID: " + boardDTO.getUserId()));
        if(boardRepository.existsByBoardNameAndPictureId(boardDTO.getBoardName(), boardDTO.getPictureId())){
            throw new SameDataException("Picture has already in the board");
        }

        Board newBoard = Board.
                builder()
                .user(existingUser)
                .boardName(boardDTO.getBoardName())
                .boardDescription(boardDTO.getBoardDescription())
                .picture(existingPicture)
                .build();
        return boardRepository.save(newBoard);

    }


    @Override
    public List<Board> getBoardOfUser(Long userId, String boardName) throws DataNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new DataNotFoundException("Cannot find user"));
        List<Board> board = boardRepository.findBoardByName(boardName);
                //.orElseThrow(()->new DataNotFoundException("Cannot find board with name: " + boardName));
        return boardRepository.findAllBoardOfUser(user.getId(), boardName);
    }

    @Override
    public int updateBoardName(Long userId, String boardName, BoardDTO boardDTO) throws DataNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new DataNotFoundException("Cannot find user"));
        List<Board>  board = boardRepository.findBoardByName(boardName);
               // .orElseThrow(()->new DataNotFoundException("Cannot find board with name: " + boardName));
        return boardRepository.updateBoardName(userId, boardName, boardDTO.getBoardName());
    }

    @Override
    public void deletePictureOutOfBoard(Long userId,  Long pictureId, String boardName)
            throws DataNotFoundException {

        if (!boardRepository.existsByUserIdAndBoardNameAndPictureId(userId, boardName, pictureId)) {
            throw new DataNotFoundException("Cannot find board with name: " + boardName +
                    ", picture ID: " + pictureId +
                    ", and user ID: " + userId);
        }

        boardRepository.deletePictureOfBoard(userId, pictureId, boardName);
    }

    @Override
    public void deleteBoardByUser(Long userId, String boardName ) throws DataNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new  DataNotFoundException
                        ("Cannot find user with id: " + userId));
        List<Board>  board = boardRepository.findBoardByName(boardName);
                //.orElseThrow(()->new DataNotFoundException("Cannot find board with name: " + boardName));
        boardRepository.deleteBoardOfUser(userId, boardName);
    }

    @Override
    public List<Picture> getAllPictureInBoard(Long userId, String boardName) throws DataNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new DataNotFoundException("Cannot find user"));
        List<Board> boardList = boardRepository.findBoardByName(boardName);
        return boardRepository.findAllPicturesInBoardByName(user.getId(), boardName);
    }
}
