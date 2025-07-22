package com.app.csapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Data Transfer Object for Picture")
public class PictureDTO {

    @NotNull(message = "User ID can not be blank")
    @JsonProperty("user_id")
    @Schema(description = "ID of the user who owns the picture", example = "1", required = true)
    private long userId;

    @JsonProperty("image_description")
    @Schema(description = "Description of the picture", example = "Beautiful sunset at the beach")
    private String imageDescription;

    @JsonProperty("image_url")
    @Schema(description = "URL of the uploaded image", example = "image123.jpg")
    private String imageUrl;

    @Size(min = 6, max = 100, message = "Title must between 6 and 100 characters")
    @Schema(description = "Title of the picture", example = "My Amazing Photo", minLength = 6, maxLength = 100)
    private String title;

    //private MultipartFile file;

}
