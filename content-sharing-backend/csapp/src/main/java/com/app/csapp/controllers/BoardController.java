package com.app.csapp.controllers;


import com.app.csapp.dtos.BoardDTO;
import com.app.csapp.exceptions.DataNotFoundException;
import com.app.csapp.exceptions.SameDataException;
import com.app.csapp.models.Board;
import com.app.csapp.models.Picture;
import com.app.csapp.services.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/boards")
//@Validated
@RequiredArgsConstructor

public class BoardController {
    private final BoardService boardService;

    // tạo bảng
    @PostMapping("")
    public ResponseEntity<?> createBoard(
            @Valid @RequestBody BoardDTO boardDTO,
            BindingResult result){
        try {
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors().stream().map(FieldError:: getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }

            Board newBoard = boardService.createBoard(boardDTO);
            return ResponseEntity.ok(newBoard);
        } catch (DataNotFoundException | SameDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    //Lấy ra tất cả board theo userId
    @GetMapping("/user/{userId}/board/{name}")
    public ResponseEntity<?> getAllBoard(
            @PathVariable("userId") Long userId,
            @PathVariable("name") String name
    ){
        try{
            List<Board> board = boardService.getBoardOfUser(userId, name);
            return ResponseEntity.ok(board);
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    //lấy ra tất cả ảnh trong board
    @GetMapping("/user/{userId}/board/{name}/picture")
    public ResponseEntity<?> getAllPictureInBoard(
            @PathVariable("userId") Long userId,
            @PathVariable("name") String name
    ){
        try{
            //System.out.println(name);
            List<Picture> pictures = boardService.getAllPictureInBoard(userId, name);
            return ResponseEntity.ok(pictures);
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/user/{userId}/board/{boardName}")
    public ResponseEntity<String> updateBoard(
            @PathVariable("userId") Long userId,
            @PathVariable("boardName") String boardName,
            @Valid @RequestBody BoardDTO boardDTO
    ){
        try{
            boardService.updateBoardName(userId, boardName, boardDTO);
            return ResponseEntity.ok("Update board name successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    @DeleteMapping("/user/{userId}/board/{boardName}/picture/{pictureId}")
    public ResponseEntity<String> deletePictureOutOfBoard(
            @PathVariable("userId") Long userId,
            @PathVariable("pictureId") Long pictureId,
            @PathVariable("boardName") String boardName
    ){
        try{
            boardService.deletePictureOutOfBoard(userId, pictureId, boardName);
            return ResponseEntity.ok("Delete picture out of board successfully");
        } catch (DataNotFoundException e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/user/{userId}/board/{boardName}")
    public ResponseEntity<String> deleteBoardOfUser(
            @PathVariable("userId") Long userId,
            @PathVariable("boardName") String boardName
    ){
        try{
            boardService.deleteBoardByUser(userId, boardName);
            return ResponseEntity.ok("Delete board successfully");
        } catch (DataNotFoundException e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
