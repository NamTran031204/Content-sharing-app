package com.app.csapp.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {
    // can lam tiep response
    @JsonProperty( "username")
    private String username;

    @JsonProperty("reference_id")
    private long referenceId;
    private String message;
}
