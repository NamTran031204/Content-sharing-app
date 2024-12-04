package com.app.csapp.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDTO {

    @NotNull(message = "User ID can not be blank")
    @JsonProperty("user_id")
    private long userId;

    @NotNull(message = "Picture ID can not be blank")
    @JsonProperty("picture_id")
    private long pictureId;

    @NotBlank(message = "Name required")
    @JsonProperty("board_name")
    private String boardName;


    @JsonProperty("board_description")
    private String boardDescription;

}
