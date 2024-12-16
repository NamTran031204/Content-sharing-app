package com.app.csapp.dtos;

import lombok.*;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopTagDTO {
    private String tagName;
    private long pictureCount;
}