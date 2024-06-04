package com.developer.recruitmentTask.config.security.jwt.pojo;

import com.developer.recruitmentTask.entity.enums.Role;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String username;
    private String password;
    private String email;
    private Role role;

}
