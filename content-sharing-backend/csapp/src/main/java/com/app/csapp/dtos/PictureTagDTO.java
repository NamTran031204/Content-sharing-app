package com.app.csapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PictureTagDTO {

    @JsonProperty("picture_id")
    private Long pictureId;

    @JsonProperty("tag_id")
   private Long tagId;
}
