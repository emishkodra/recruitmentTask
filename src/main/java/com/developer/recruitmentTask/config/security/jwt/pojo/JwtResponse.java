package com.developer.recruitmentTask.config.security.jwt.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

    @JsonProperty("access_token")
    private String jwtToken;

}
