package com.app.csapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PictureDTO {


    @NotNull(message = "User ID can not be blank")
    @JsonProperty("user_id")
    private long userId;

    @JsonProperty("image_description")
    private String imageDescription;

    @JsonProperty("image_url")
    private String imageUrl;

    @Size(min = 6, max = 100, message = "Title must between 6 and 100 characters")
    private String title;

    //private MultipartFile file;

}
