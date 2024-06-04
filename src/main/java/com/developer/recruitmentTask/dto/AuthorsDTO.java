package com.developer.recruitmentTask.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorsDTO {
    private Long id;
    private String firstName;
    private String lastName;
    @JsonIgnore
    private Boolean status;
}
