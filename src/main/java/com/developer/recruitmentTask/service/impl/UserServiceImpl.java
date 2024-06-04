package com.developer.recruitmentTask.service.impl;

import com.developer.recruitmentTask.dao.UserDao;
import com.developer.recruitmentTask.dto.UserDTO;
import com.developer.recruitmentTask.entity.User;
import com.developer.recruitmentTask.entity.enums.Role;
import com.developer.recruitmentTask.mapper.UserMapper;
import com.developer.recruitmentTask.service.UserService;
import com.developer.recruitmentTask.util.error.BadRequestAlertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.developer.recruitmentTask.util.error.ErrorConstants.*;
import static com.developer.recruitmentTask.util.error.ErrorConstants.NOT_FOUND_KEY;
import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserDTO> findAllUsers(){
        List<User> users = userDao.findAllByOrderByIdDesc(); // get all users ordered by their ID in descending order
        return userMapper.toDto(users);
    }

    @Override
    public List<UserDTO> findAllAdminUsers(){
        List<User> admins = userDao.findByRoleOrderByIdDesc(Role.ADMIN); // get all ADMIN users ordered by their ID in descending order
        return userMapper.toDto(admins);
    }

    @Override
    public List<UserDTO> findAllUserUsers(){
        List<User> user = userDao.findByRoleOrderByIdDesc(Role.USER); // get all USER users ordered by their ID in descending order
        return userMapper.toDto(user);
    }

    @Override
    public UserDTO getUserById(Long userId){
        // check if the provided userId is null or 0
        if (userId == null || userId == 0) {
            throw new BadRequestAlertException(ID_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
        }
        // attempt to find the existing user in the db with the specified ID
        Optional<User> user = userDao.findById(userId);

        // map Optional<User> to UsersDTO using a mapper
        return userMapper.toDto(user.orElseThrow(() ->
                new BadRequestAlertException(USER_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY)));  // if user not found -> throw an exception.
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Long userId) {
        // check for userId
        if (userId == null || userId == 0) {
            throw new BadRequestAlertException(ID_NOT_FOUND_MESSAGE, ENTITY_NAME, BAD_REQUEST_KEY);
        }

        // attempt to find the existing user in the db with the specified ID
        Optional<User> existingUser = userDao.findById(userId);
        if (existingUser.isEmpty()) {
            throw new BadRequestAlertException(USER_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
        }
        // get user entity from Optional and update it
        User update = existingUser.get();
        update.setUsername(userDTO.getUsername());
        update.setEmail(userDTO.getEmail());

        // check if the current role is ADMIN and if so don't change it
        if (update.getRole() != Role.ADMIN) {
            if (userDTO.getAuthorities() != null) {
                try {
                    Role role = Role.valueOf(userDTO.getAuthorities());  // convert string to enum
                    update.setRole(role);
                } catch (IllegalArgumentException e) {
                    throw new BadRequestAlertException(INVALID_ROLE_MESSAGE, ENTITY_NAME, BAD_REQUEST_KEY);
                }
            } else {
                // set default role if none is specified and current is not ADMIN
                update.setRole(Role.USER);
            }
        }
            // save the updated user and convert it back to DTO
            userDao.save(update);
            return userMapper.toDto(update);
        }

    @Override
    public void deleteUser(Long userId) {
        // check if the provided userId is not null or 0
        if (userId == null || userId == 0) {
                throw new BadRequestAlertException(ID_NOT_FOUND_MESSAGE, ENTITY_NAME, BAD_REQUEST_KEY);
            }
            // check if the user exists in the db
            boolean exists = userDao.existsById(userId);
            if (!exists) {
                throw new BadRequestAlertException(USER_NOT_FOUND_MESSAGE, ENTITY_NAME, NOT_FOUND_KEY);
            }
            // if it exists completely delete it from the db
            userDao.deleteById(userId);
    }
}
