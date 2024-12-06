package com.app.csapp.controllers;

import com.app.csapp.dtos.PictureDTO;
import com.app.csapp.models.Picture;
import com.app.csapp.services.IPictureService;
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
@RequestMapping("api/v1/pictures")
@RequiredArgsConstructor
public class PictureController {
    private final IPictureService pictureService;

    @PostMapping("")
    public ResponseEntity<?> createTags(
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
    public ResponseEntity<?> uploadPicture(
            @Valid @ModelAttribute PictureDTO pictureDTO,
            @PathVariable("picture_id") Long pictureId,
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
                    existingPicture.getId(),
//                        pictureDTO
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
    public ResponseEntity<List<Picture>> getAllImage(
            @RequestParam("Page") int page,
            @RequestParam("Limit") int limit
    ){
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("createTime").descending());
        Page<Picture> picturePage = pictureService.getAllImage(pageRequest);
        int totalPages = picturePage.getTotalPages();
        List<Picture> pictures = picturePage.getContent();
        return ResponseEntity.ok(pictures);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getImage(@PathVariable("id") Long id){
        try {
            Picture picture = pictureService.getImageById(id);
            return ResponseEntity.ok(picture);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable Long id){
        pictureService.deleteImage(id);
        return ResponseEntity.ok(String.format("Delete Image id = %d",id));
    }

}
