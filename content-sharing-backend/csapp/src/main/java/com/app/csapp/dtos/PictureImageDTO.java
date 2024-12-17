package com.app.csapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PictureImageDTO {
    @JsonProperty("picture_id")
    private Long pictureId;

    @JsonProperty("image_url")
    private String imageUrl;
}
