package com.developer.recruitmentTask.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private Boolean status;
    private String authorities;
}
