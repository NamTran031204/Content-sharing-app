package com.app.csapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for User registration and updates")
public class UserDTO {

    @NotBlank(message = "name cannot be blank")
    @Schema(description = "User's full name", example = "John Doe", required = true)
    private String name;

    @JsonProperty("email")
    @Schema(description = "User's email address", example = "john.doe@email.com")
    private String email;

    @NotBlank(message = "username cannot be blank")
    @JsonProperty("username")
    @Schema(description = "Unique username", example = "johndoe123", required = true)
    private String userName;

    @JsonProperty("description")
    @Schema(description = "User bio/description", example = "Photography enthusiast")
    private String description;

    @NotBlank(message = "ban can nhap mat khau")
    @Schema(description = "User password", example = "password123", required = true)
    private String password;

    @JsonProperty("retype_password")
    @NotBlank(message = "ban can nhap lai mat khau")
    @Schema(description = "Password confirmation", example = "password123", required = true)
    private String retypePassword;

    //@JsonProperty("profile_picture")
    @Schema(description = "Profile picture filename", example = "profile.jpg")
    private String profilePicture;

    @JsonProperty("create_time")
    @Schema(description = "Account creation timestamp")
    private LocalDateTime createTime;

    @JsonProperty("role_id")
    @Schema(description = "User role ID", example = "1")
    private long roleId;

}
