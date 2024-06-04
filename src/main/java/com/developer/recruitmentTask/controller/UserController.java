package com.developer.recruitmentTask.controller;

import com.developer.recruitmentTask.dto.UserDTO;
import com.developer.recruitmentTask.service.UserService;
import com.developer.recruitmentTask.util.error.BadRequestAlertException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.developer.recruitmentTask.util.error.ErrorConstants.*;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "User Controller", description = "The User API With Description Tag Annotation")
public class UserController {

    private static final String ENTITY_NAME = "user";

    @Autowired
    private UserService userService;

    @Operation(summary = "Get All Users", description = "Only user ROLE_ADMIN can access this endpoint")
    @GetMapping(value = "/all")
    public List<UserDTO> allUsers() {
        return userService.findAllUsers();
    }

    @Operation(summary = "Get All Users For ROLE_ADMIN", description = "Only User ROLE_ADMIN Can Access This Endpoint")
    @GetMapping(value = "/all/admin")
    public List<UserDTO> allAdminUsers() {
        return userService.findAllAdminUsers();
    }

    @Operation(summary = "Get All Users For ROLE_USER", description = "Only User ROLE_ADMIN Can Access This Endpoint")
    @GetMapping(value = "/all/user")
    public List<UserDTO> allUserUsers() {
        return userService.findAllUserUsers();
    }

    @Operation(summary = "Get User By Id", description = "Only User ROLE_ADMIN Can Access This Endpoint")
    @GetMapping(value = "/find/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        if (userId == null){
            throw new BadRequestAlertException(ID_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
        }
        UserDTO user = userService.getUserById(userId);
        if (user == null) {
            throw new BadRequestAlertException(USER_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
        }
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Update User With Specific Id", description = "Only User ROLE_ADMIN Can Access This Endpoint")
    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
        if (userDTO == null || userId == null) {
            throw new BadRequestAlertException(ID_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
        }
        UserDTO updatedUser = userService.updateUser(userDTO, userId);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Delete User By Id", description = "Only User ROLE_ADMIN Can Access This Endpoint")
    @DeleteMapping(value = "/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        if (userId == null) {
            throw new BadRequestAlertException(ID_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
        }
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
