package com.app.csapp.responses;

import com.app.csapp.models.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String name;

    @JsonProperty("username")
    private String userName;

    private String description;
    private String profilePicture;

    public static UserResponse fromUser(User user){
        UserResponse userResponse = UserResponse.builder()
                .userName(user.getUserName())
                .name(user.getName())
                .description(user.getDescription())
                .profilePicture(user.getProfilePicture())
                .build();
        return userResponse;
    }
}
