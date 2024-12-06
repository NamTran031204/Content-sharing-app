package com.app.csapp.controllers;


import com.app.csapp.dtos.BoardDTO;
import com.app.csapp.exceptions.DataNotFoundException;
import com.app.csapp.models.Board;
import com.app.csapp.services.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/boards")
//@Validated
@RequiredArgsConstructor

public class BoardController {
    private final BoardService boardService;
    @PostMapping("")
    public ResponseEntity<?> createTags(
            @Valid @RequestBody BoardDTO boardDTO,
            BindingResult result){
        try {
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors().stream().map(FieldError:: getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }

            Board newBoard = boardService.createBoard(boardDTO);
            return ResponseEntity.ok(newBoard);
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("") //http://localhost:8088/api/v1/boards
    public ResponseEntity<List<Board>> getAllTags(
//            @RequestParam("page") int page,
//            @RequestParam("limit") int limit
    ){
        List<Board> board = boardService.getAllBoard();
        return ResponseEntity.ok(board);
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> createTag(
            @PathVariable Long id,
            @Valid @RequestBody BoardDTO boardDTO
    ){
        //tagService.updateTag(id, tagDTO);
        return ResponseEntity.ok("Update Tags successfully");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTags(@PathVariable Long id){
        boardService.deleteBoard(id);
        return ResponseEntity.ok("Delete successfully");
    }
}
