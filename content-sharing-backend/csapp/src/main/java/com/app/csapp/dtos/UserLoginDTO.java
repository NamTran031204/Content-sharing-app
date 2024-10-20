package com.app.csapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {
    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String password;
}
