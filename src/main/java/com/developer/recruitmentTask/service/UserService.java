package com.developer.recruitmentTask.service;

import com.developer.recruitmentTask.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> findAllUsers();

    List<UserDTO> findAllAdminUsers();

    List<UserDTO> findAllUserUsers();

    UserDTO getUserById(Long userId);

    UserDTO updateUser(UserDTO userDTO, Long userId);

    void deleteUser(Long userId);
}
