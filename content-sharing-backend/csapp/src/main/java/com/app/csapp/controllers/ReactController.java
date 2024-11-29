package com.app.csapp.controllers;

import com.app.csapp.dtos.BoardDTO;
import com.app.csapp.dtos.ReactDTO;
import com.app.csapp.enums.ReactEnums;
import com.app.csapp.exceptions.DataNotFoundException;
import com.app.csapp.models.React;
import com.app.csapp.services.ReactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/reacts")
@RequiredArgsConstructor

public class ReactController {
    private final ReactService reactService;
    @PostMapping("/{id}")
    public ResponseEntity<?> createReact(
            @Valid @RequestBody ReactDTO reactDTO,
            BindingResult result
    ){
    try {

        if(result.hasErrors()){
            List<String> errorMessages = result.getFieldErrors().stream().map(FieldError:: getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }

            React newReact = reactService.createReact(reactDTO);

            if(newReact.getContent().equals("SHARE")){
                return ResponseEntity.ok("Share successful. Link: " + newReact.getContent());
            }

            return ResponseEntity.ok(newReact);
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }






}
