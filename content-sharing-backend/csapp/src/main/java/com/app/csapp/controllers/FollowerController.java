package com.app.csapp.controllers;

import com.app.csapp.dtos.FollowerDTO;
import com.app.csapp.models.Follower;
import com.app.csapp.models.User;
import com.app.csapp.responses.UserListResponse;
import com.app.csapp.responses.UserResponse;
import com.app.csapp.services.IFollowerService;
import com.app.csapp.services.IUserService;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/follower")
@RequiredArgsConstructor
public class FollowerController {
    private final IFollowerService followerService;
    private final IUserService userService;

    //Follow user
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
            followerService.createFollow(followerDTO.getFollowerId(), followerDTO.getFollowingId());
            return ResponseEntity.ok("ban da follow "+ followerDTO.getFollowingId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/unfollow")
    public ResponseEntity<?> deleteFollow(
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
            followerService.unfollowUser(followerDTO.getFollowerId(), followerDTO.getFollowingId());
            return ResponseEntity.ok("ban da unfollow " + followerDTO.getFollowingId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/follower_list/{followerId}")
    public ResponseEntity<List<UserResponse>> getFollowerList(
            @Valid @PathVariable("followerId") long followerId,
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        try{
            PageRequest pageRequest = PageRequest.of(
                    page, limit, Sort.by("followedAt").descending());
            Page<UserResponse> userPage = followerService.findAllByFollowerId(followerId, pageRequest);

            int totalPages = userPage.getTotalPages();
            List<UserResponse> users = userPage.getContent();

            return ResponseEntity.ok(UserListResponse.builder()
                    .users(users)
                    .totalPages(totalPages)
                    .build().getUsers());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/following_list/{followingId}")
    public ResponseEntity<List<UserResponse>> getFollowingList(
            @Valid @PathVariable("followingId") long followingId,
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        try{
            PageRequest pageRequest = PageRequest.of(
                    page, limit, Sort.by("followedAt").descending());
            Page<UserResponse> userPage = followerService.findAllByFollowingId(followingId, pageRequest);

            int totalPages = userPage.getTotalPages();
            List<UserResponse> users = userPage.getContent();

            return ResponseEntity.ok(UserListResponse.builder()
                    .users(users)
                    .totalPages(totalPages)
                    .build().getUsers());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
