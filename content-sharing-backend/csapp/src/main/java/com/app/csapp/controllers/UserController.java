package com.app.csapp.controllers;

import com.app.csapp.dtos.*;

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
@RequestMapping("api/v1/users")
public class UserController {

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
            return ResponseEntity.ok("register successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody UserLoginDTO userLoginDTO,
            BindingResult result
    ){
        try{
            // de sau nhe
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
