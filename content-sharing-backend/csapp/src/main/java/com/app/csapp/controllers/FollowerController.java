package com.app.csapp.controllers;

import com.app.csapp.dtos.FollowerDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/followers")
public class FollowerController {
    @PostMapping("/follow")
    public ResponseEntity<?> createFollow(
            @Valid @RequestBody FollowerDTO followerDTO,
            BindingResult result
    ){
        try{
            if(result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            if(followerDTO.getFollowerId() == followerDTO.getFollowingId()){
                return ResponseEntity.badRequest().body("ban khong the follow chinh minh :)");
            }

            followerDTO.builder()
                    .followingId(followerDTO.getFollowingId())
                    .followerId(followerDTO.getFollowerId())
                    .build();
            return ResponseEntity.ok("ban da follow "+ followerDTO.getFollowingId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
