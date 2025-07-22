
package com.app.csapp.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data Transfer Object for Tag")
public class TagDTO {
    @NotEmpty(message = "Tag name can not be empty")
    @Schema(description = "Name of the tag", example = "Photography", required = true)
    private String tagName;
}
