package com.app.csapp.controllers;

import com.app.csapp.dtos.TagDTO;
import com.app.csapp.services.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Tag Management", description = "APIs for managing content tags")
public class TagController {

    private final TagService tagService;

    @PostMapping("")
    @Operation(
        summary = "Create new tag",
        description = "Create a new content tag"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tag created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<?> createTags(
            @Parameter(description = "Tag data")
            @Valid @RequestBody TagDTO tagDTO,
            BindingResult result){
        if(result.hasErrors()){
            List<String> errorMessages = result.getFieldErrors().stream().map(FieldError :: getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        com.app.csapp.models.Tag tag = tagService.createTag(tagDTO);
        return ResponseEntity.ok(tag);
    }

    @GetMapping("") //http://localhost:8088/api/v1/tags
    @Operation(
        summary = "Get all tags",
        description = "Retrieve a list of all available tags"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tags retrieved successfully")
    })
    public ResponseEntity<List<com.app.csapp.models.Tag>> getAllTags(
            @Parameter(description = "Page number (currently unused)")
            @RequestParam("page") int page,
            @Parameter(description = "Items per page (currently unused)")
            @RequestParam("limit") int limit
    ){
        List<com.app.csapp.models.Tag> tags = tagService.getAllTag();
        return ResponseEntity.ok(tags);
    }


    @PutMapping("/{id}")
    @Operation(
        summary = "Update tag",
        description = "Update an existing tag by ID"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tag updated successfully"),
        @ApiResponse(responseCode = "400", description = "Tag not found or invalid data")
    })
    public ResponseEntity<String> createTag(
            @Parameter(description = "Tag ID")
            @PathVariable Long id,
            @Parameter(description = "Updated tag data")
            @Valid @RequestBody TagDTO tagDTO
    ){
        tagService.updateTag(id, tagDTO);
        return ResponseEntity.ok("Update Tags successfully");
    }
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete tag",
        description = "Delete a tag by its ID"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tag deleted successfully"),
        @ApiResponse(responseCode = "400", description = "Tag not found")
    })
    public ResponseEntity<String> deleteTags(
            @Parameter(description = "Tag ID to delete")
            @PathVariable Long id
    ){
        tagService.deleteTag(id);
        return ResponseEntity.ok("Delete successfully");
    }

}
