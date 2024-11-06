package com.app.csapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class FollowerDTO {
    @NotNull(message = "require follower_id")
    @JsonProperty("follower_id")
    private long followerId;

    @NotNull(message = "hay theo doi ai do (require following_id)")
    @JsonProperty("following_id")
    private long followingId;

}
