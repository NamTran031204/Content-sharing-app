package com.app.csapp.controllers;

import com.app.csapp.dtos.TagDTO;
import com.app.csapp.models.Tag;
import com.app.csapp.services.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/tags")
//@Validated
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping("")
    public ResponseEntity<?> createTags(
            @Valid @RequestBody TagDTO tagDTO,
            BindingResult result){
        if(result.hasErrors()){
            List<String> errorMessages = result.getFieldErrors().stream().map(FieldError :: getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        Tag tag = tagService.createTag(tagDTO);
        return ResponseEntity.ok(tag);
    }

    @GetMapping("") //http://localhost:8088/api/v1/tags
    public ResponseEntity<List<Tag>> getAllTags(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        List<Tag> tags = tagService.getAllTag();
        return ResponseEntity.ok(tags);
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> createTag(
            @PathVariable Long id,
            @Valid @RequestBody TagDTO tagDTO
    ){
        tagService.updateTag(id, tagDTO);
        return ResponseEntity.ok("Update Tags successfully");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTags(@PathVariable Long id){
        tagService.deleteTag(id);
        return ResponseEntity.ok("Delete successfully");
    }

}
