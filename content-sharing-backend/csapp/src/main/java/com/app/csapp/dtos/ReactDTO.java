package com.app.csapp.dtos;


import com.app.csapp.enums.ReactEnums;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReactDTO {

    @JsonProperty("user_id")
    @NotNull(message =  "User id cannot be null")
    private Long userId;

    @JsonProperty("picture_id")
    @NotNull(message = "Picture id cannot be null")
    private Long pictureId;

    @NotBlank
    @JsonProperty("type_react")
    private ReactEnums typeReact;

    @JsonProperty("content")
    private String content;
}
