package com.app.csapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for User login")
public class UserLoginDTO {
    @Schema(description = "User's email address", example = "john.doe@email.com", required = true)
    private String email;

    @Schema(description = "User's password", example = "password123", required = true)
    private String password;
}
