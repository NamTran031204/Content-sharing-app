package com.app.csapp.responses;

import com.app.csapp.models.React;
import com.app.csapp.models.User;
import com.app.csapp.repositories.ReactRepository;
import com.app.csapp.services.IReactService;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReactResponse {
    private String name;

    @JsonProperty("username")
    private String userName;
    private String content;
    private String profilePicture;


    public static ReactResponse fromReact(React react){

        ReactResponse reactResponse = ReactResponse.builder()
                .userName(react.getUser().getUserName())
                .name(react.getUser().getName())
                .profilePicture(react.getUser().getProfilePicture())
                .content(react.getContent())
                .build();
        return reactResponse;
    }

}