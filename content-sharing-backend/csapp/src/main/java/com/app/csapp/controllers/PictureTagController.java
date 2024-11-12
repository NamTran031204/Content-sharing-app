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

import java.util.List;

@RestController
@RequestMapping("api/v1/pictureTags")
//@Validated
@RequiredArgsConstructor
public class PictureTagController {
    private final PictureTagService pictureTagService;
    @PostMapping("")
    public ResponseEntity<?> createTags(

            @Valid @RequestBody PictureTagDTO pictureTagDTO,
            BindingResult result){
        if(result.hasErrors()){
            List<String> errorMessages = result.getFieldErrors().stream().map(FieldError:: getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        try {
            pictureTagService.createPictureTag(pictureTagDTO);
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("ok");
    }

    @GetMapping("") //http://localhost:8088/api/v1/tags
    public ResponseEntity<List<PictureTag>> getAllTags(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        List<PictureTag> tags = pictureTagService.getAllPictureTag();
        return ResponseEntity.ok(tags);
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> createTag(
            @PathVariable Long id,
            @Valid @RequestBody PictureTagDTO pictureTagDTO
    ){
        pictureTagService.updatePictureTag(id, pictureTagDTO);
        return ResponseEntity.ok("Update Tags successfully");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTags(@PathVariable Long id){
        pictureTagService.deleteTag(id);
        return ResponseEntity.ok("Delete successfully");
    }
}
