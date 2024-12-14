package com.app.csapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class UserDTO {

    @NotBlank(message = "name cannot be blank")
    private String name;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("email")
    private String email;

    @NotBlank(message = "username cannot be blank")
    @JsonProperty("username")
    private String userName;

    @JsonProperty("description")
    private String description;

    @NotBlank(message = "ban can nhap mat khau")
    private String password;

    @JsonProperty("retype_password")
    @NotBlank(message = "ban can nhap lai mat khau")
    private String retypePassword;

    //@JsonProperty("profile_picture")
    private String profilePicture;

    @JsonProperty("create_time")
    private LocalDateTime createTime;

    @JsonProperty("role_id")
    private long roleId;

}
