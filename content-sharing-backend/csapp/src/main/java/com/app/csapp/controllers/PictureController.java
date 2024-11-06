package com.app.csapp.controllers;

import com.app.csapp.dtos.PictureDTO;
import com.app.csapp.models.Picture;
import com.app.csapp.services.IPictureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
            //@ModelAttribute MultipartFile file,
            BindingResult result
            //@RequestPart("file")MultipartFile file
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

    @PostMapping(value = "uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadPicture(
            @PathVariable("id") Long pictureId,
            @ModelAttribute MultipartFile file
    ){
        //MultipartFile file = pictureDTO.getFile();
        try {
            Picture existingPicture = pictureService.getImageById(pictureId);
            if(file != null){
                //Kiem tra kich thuoc file va dinh dang
                if(file.getSize() > 10 * 1024 * 1024){
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("Maximum size is 10MB only");
                }
                String contentFile = file.getContentType();
                if(contentFile == null || !contentFile.startsWith("image/")){
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("File must be an Image");
                }
                String filename = storeFile(file);
                Picture pictureUrl = pictureService.createPictureUrl(
                        existingPicture.getId(),
                        PictureDTO.builder()
                                .imageUrl(filename).build());
            }
            return ResponseEntity.ok("ok");
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
    public ResponseEntity<String> getAllImage(){
//        @RequestParam("Page") int page;
//        @RequestParam("Limit") int limit;
        return ResponseEntity.ok("getAllImage");
    }


    @GetMapping("/{id}")
    public ResponseEntity<String> updateImage(@PathVariable Long id){
        return ResponseEntity.ok("Get Image id = " + id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable Long id){
        return ResponseEntity.ok(String.format("Delete Image id = %d",id));
    }

}
