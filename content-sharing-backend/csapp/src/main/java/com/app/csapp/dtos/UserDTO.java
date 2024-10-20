package com.app.csapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotBlank(message = "username cannot be blank")
    private String name;

    @NotBlank(message = "email cannot be blank")
    private String email;

    @NotBlank(message = "username cannot be blank")
    @JsonProperty("username")
    private String userName;

    @NotBlank(message = "ban can nhap mat khau")
    private String password;

    @JsonProperty("retype_password")
    @NotBlank(message = "ban can nhap lai mat khau")
    private String retypePassword;

    @JsonProperty("profile_picture")
    private String profilePicture;

    @JsonProperty("created_at")
    private Date createdAt;
}
