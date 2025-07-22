package com.app.csapp.controllers;

import com.app.csapp.dtos.PictureDTO;
import com.app.csapp.models.Picture;
import com.app.csapp.services.IPictureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/pictures")  //api.prefix = http://localhost:8088/api/v1/picture/id
@RequiredArgsConstructor
@Tag(name = "Picture Management", description = "APIs for managing pictures and image uploads")
public class PictureController {
    private final IPictureService pictureService;

    // tao anh, dang anh
    @PostMapping("")
    @Operation(
        summary = "Create new picture",
        description = "Create a new picture entry with metadata"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Picture created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<?> createPicture(
            @Parameter(description = "Picture metadata")
            @Valid @RequestBody PictureDTO pictureDTO,
            BindingResult result
    ){
        try{
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors().stream().map(FieldError :: getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Picture newPicture = pictureService.createPicture(pictureDTO);
            return ResponseEntity.ok(newPicture);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping(value = "uploads/{picture_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
        summary = "Upload picture file",
        description = "Upload an image file for an existing picture entry"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Image uploaded successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid file or picture not found"),
        @ApiResponse(responseCode = "413", description = "File size too large"),
        @ApiResponse(responseCode = "415", description = "Unsupported file type")
    })
    public ResponseEntity<?> uploadPicture(
            @Parameter(description = "Picture metadata")
            @Valid @ModelAttribute PictureDTO pictureDTO,
            @Parameter(description = "Picture ID")
            @PathVariable("picture_id") Long pictureId,
            @Parameter(description = "Image file to upload")
            @RequestParam("file") MultipartFile file
    ){
        //MultipartFile file = pictureDTO.getFile();
        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest().body("File cannot be null or empty");
            }
            Picture existingPicture = pictureService.getImageById(pictureId);

            //Kiem tra kich thuoc file va dinh dang
            if(file.getSize() > 10 * 1024 * 1024){
                return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("Maximum size is 10MB only");
            }

            String contentFile = file.getContentType();
            if(contentFile == null || !contentFile.startsWith("image/")){
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("File must be an Image");
            }

            String filename = storeFile(file);
            // Update the existing picture with the new image URL and details
            pictureDTO.setImageUrl(filename);
            Picture updatedPicture = pictureService.updateImage(
                    pictureId,
                    PictureDTO.builder()
                            .imageUrl(filename)
                            .imageDescription(existingPicture.getImageDescription())
                            .title(existingPicture.getTitle())// Update as needed
                            .build()
            );
            return ResponseEntity.ok(pictureDTO.getImageUrl());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String storeFile(MultipartFile file) throws IOException{
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        //THEM UUID TRUOC FILE DE DAM BAO FILE LA DUY NHAT
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        //DUONG DAN DEN THU MUC LUU FILE
        java.nio.file.Path uploadDir = Paths.get("uploads");
        //KIEM TRA THU MUC TON TAI CHUA
        if(!Files.exists(uploadDir)){
            Files.createDirectories(uploadDir);
        }
        //DUONG DAN DEN FILE
        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }

    @GetMapping("") //http://localhost:8088/api/v1/pictures
    @Operation(
        summary = "Get all pictures",
        description = "Retrieve paginated list of all pictures"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pictures retrieved successfully")
    })
    public ResponseEntity<List<Picture>> getAllImage(
            @Parameter(description = "Page number (0-based)")
            @RequestParam("Page") int page,
            @Parameter(description = "Number of items per page")
            @RequestParam("Limit") int limit
    ){
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("createTime").descending());
        Page<Picture> picturePage = pictureService.getAllImage(pageRequest);
        int totalPages = picturePage.getTotalPages();
        List<Picture> pictures = picturePage.getContent();
        return ResponseEntity.ok(pictures);
    }

    // lấy ra ảnh theo userId
    @GetMapping("/getPicture/{id}")
    @Operation(
        summary = "Get picture by ID",
        description = "Retrieve a specific picture by its ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Picture found successfully"),
        @ApiResponse(responseCode = "400", description = "Picture not found")
    })
    public ResponseEntity<?> getImage(
            @Parameter(description = "Picture ID")
            @PathVariable("id") Long id
    ){
        try {
            Picture picture = pictureService.getImageById(id);
            return ResponseEntity.ok("picture");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/update/{id}")
    @Operation(
        summary = "Update picture",
        description = "Update picture metadata and information"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Picture updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data or picture not found")
    })
    public ResponseEntity<?> updateImage(
            @Parameter(description = "Picture ID")
            @PathVariable("id") Long id,
            @Parameter(description = "Updated picture data")
            @RequestBody PictureDTO pictureDTO
    ){
        try{
            Picture newPicture = pictureService.updateImage(id, pictureDTO);
            return ResponseEntity.ok("Update complete");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete picture",
        description = "Delete a picture by its ID"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Picture deleted successfully"),
        @ApiResponse(responseCode = "400", description = "Picture not found")
    })
    public ResponseEntity<String> deleteImage(
            @Parameter(description = "Picture ID to delete")
            @PathVariable Long id
    ){
        pictureService.deleteImage(id);
        return ResponseEntity.ok(String.format("Delete Image id = %d",id));
    }


}
