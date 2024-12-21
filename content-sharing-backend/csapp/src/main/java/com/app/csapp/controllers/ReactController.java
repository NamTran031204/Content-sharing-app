package com.app.csapp.controllers;

import com.app.csapp.dtos.ReactDTO;
import com.app.csapp.exceptions.DataNotFoundException;
import com.app.csapp.models.React;
import com.app.csapp.responses.ReactListResponse;
import com.app.csapp.responses.ReactResponse;
import com.app.csapp.responses.UserListResponse;
import com.app.csapp.responses.UserResponse;
import com.app.csapp.services.ReactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/reacts")
@RequiredArgsConstructor
public class ReactController {
    private final ReactService reactService;
    @PostMapping("")
    public ResponseEntity<?> createReact(
            @Valid @RequestBody ReactDTO reactDTO,
            BindingResult result
    ){
        try {
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors().stream().map(FieldError:: getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }

            React newReact = reactService.createReact(reactDTO);

            if(newReact.getContent().equals("SHARE")){
                return ResponseEntity.ok("Share successful. Link: " + newReact.getContent());
            }

            return ResponseEntity.ok("newReact");
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{picture_id}")
    public ResponseEntity<?> deleteReact(
            @Valid @RequestBody ReactDTO reactDTO
    ){
        try{
            reactService.deteleReact(reactDTO);
            return ResponseEntity.ok("deleted");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/{picture_id}/{react_id}")
    public ResponseEntity<?> getReact(
            @Valid @PathVariable("picture_id") long pictureId,
            @Valid @PathVariable("react_id") long reactId,
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        try{
            PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("createTime").descending());
            if(reactId == 0){
                Page<UserResponse> userPage = reactService.getAllLike(pictureId, pageRequest);
                int totalPages = userPage.getTotalPages();
                List<UserResponse> users = userPage.getContent();
                return ResponseEntity.ok(UserListResponse.builder()
                        .users(users)
                        .totalPages(totalPages)
                        .totalUsers((int)userPage.getTotalElements())
                        .build());
            }
            if(reactId == 2){
                List<ReactResponse> reactResponses = reactService.getAllComment(pictureId, pageRequest);
                return ResponseEntity.ok(reactResponses);
            }
            return ResponseEntity.ok("there are not thing to get");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
