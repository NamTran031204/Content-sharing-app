package com.app.csapp.controllers;

import com.app.csapp.dtos.PictureTagDTO;
import com.app.csapp.dtos.TagDTO;
import com.app.csapp.exceptions.DataNotFoundException;
import com.app.csapp.models.PictureTag;
import com.app.csapp.models.Tag;
import com.app.csapp.services.PictureTagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/pictureTags")
//@Validated
@RequiredArgsConstructor
public class PictureTagController {
    private final PictureTagService pictureTagService;
    @PostMapping("")
    public ResponseEntity<?> createPictureTags(
            @Valid @RequestBody PictureTagDTO pictureTagDTO,
            BindingResult result
    ){
        try{
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors().stream().map(FieldError:: getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            List<PictureTag> pictureTag = pictureTagService.createPictureTag(pictureTagDTO);
            return ResponseEntity.ok(pictureTag);
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{pictureId}")
    public ResponseEntity<?> getAllPictureTags(
            @PathVariable("pictureId") Long pictureId
    ){
        try {
            List<PictureTag> pictureTag = pictureTagService.getAllPictureTag(pictureId);
            return ResponseEntity.ok(pictureTag);
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage()) ;
        }

    }

    @DeleteMapping("/picture/{pictureId}")
    public ResponseEntity<?> deleteAllPictureTagByPicture(@PathVariable("pictureId") Long pictureId){
        try {
            pictureTagService.deleteAllPictureTagofPicture(pictureId);
            return ResponseEntity.ok("Delete successfully");
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @DeleteMapping("/picture/{pictureId}/tag/{tagId}")
    public ResponseEntity<?> deleteTagWithPicture(
            @PathVariable("pictureId") Long pictureId,
            @PathVariable("tagId") Long tagId
    ){
        try{
            pictureTagService.deleteTagWithPicture(pictureId, tagId);
            return ResponseEntity.ok("Delete tag successfully");
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
