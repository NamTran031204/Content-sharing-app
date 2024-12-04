
package com.app.csapp.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO {
    @NotEmpty(message = "Tag name can not be empty")
    private String tagName;
}
