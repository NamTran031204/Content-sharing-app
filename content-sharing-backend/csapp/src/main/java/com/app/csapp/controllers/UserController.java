package com.app.csapp.controllers;

import com.app.csapp.dtos.*;

import com.app.csapp.exceptions.DataNotFoundException;
import com.app.csapp.models.*;
import com.app.csapp.repositories.UserRepository;
import com.app.csapp.responses.UserResponse;
import com.app.csapp.services.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {

    private final IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(
            // @RequestBody: lấy dữ liệu từ thẻ body của request(postman)
            // @Valid: kiểm tra dữ liệu trước khi vào controller, nếu không hợp lệ thì trả về lỗi 400: bad request
            @Valid @RequestBody UserDTO userDTO,
            BindingResult result                // tra ve cac loi trong Valid
    ){
        try{
            if (result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            // kiem tra nhap lai mk
            if(!userDTO.getPassword().equals(userDTO.getRetypePassword())){
                return ResponseEntity.badRequest().body("mat khau xac thuc khong dung");
            }
            userDTO.setProfilePicture("default_profile_image.jpg");
            userService.createUser(userDTO);
            return ResponseEntity.ok("register successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUserImage(
            @Valid @PathVariable("id") long userId,
            @RequestParam("files") MultipartFile image
    ){
        try{
            //lay ra userId
            User existingUser = userService.getUserById(userId);

            if (image == null && image.isEmpty()) {
                return ResponseEntity.badRequest().body("Vui lòng tải lên một ảnh");
            }
            String contentType = image.getContentType();
            if(contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                        .body("File must be an image");
            }
            String imageName = storeFile(image);


            User userImage = userService.updateUser(
                    userId,
                    UserDTO.builder()
                        .profilePicture(imageName)
                        .build()
            );
            return ResponseEntity.ok("cap nhat user image thanh cong");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ham luu tru file anh
    private String storeFile(MultipartFile image) throws IOException {
        String imageName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
        // Thêm UUID vào trước tên file để đảm bảo tên file là duy nhất
        String uniqueFilename = UUID.randomUUID().toString() + "_" + imageName;
        // Đường dẫn đến thư mục mà bạn muốn lưu file
        java.nio.file.Path uploadDir = Paths.get("uploads");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        // Đường dẫn đầy đủ đến file
        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        // Sao chép file vào thư mục đích
        Files.copy(image.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }

    @PutMapping("/update/infor/{id}")
    public ResponseEntity<?> updateUser(
            @Valid @PathVariable("id") long userId,
            @RequestBody UserDTO userDTO,
            BindingResult result
    ){
        try{
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }

            userService.updateUser(userId, userDTO);
            return ResponseEntity.ok("cap nhat thong tin user thanh cong");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Hiện thông tin user
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(
            @Valid @PathVariable("id") long userId
    ){
        try{
            User existingUser =  userService.getUserById(userId);
            return ResponseEntity.ok(UserResponse.fromUser(existingUser)); // sau viet ProductResponse thi cho tra ve ProductResponse
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody UserLoginDTO userLoginDTO
    ){
        try{
            String token = userService.login(userLoginDTO.getEmail(), userLoginDTO.getPassword());
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(
            @Valid @PathVariable("id") Long userId
    ){
        try {
            User existingUser = userService.getUserById(userId);
            userService.deleteUser(userId);
            return ResponseEntity.ok("xoa user thanh cong");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
