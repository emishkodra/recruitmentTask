package com.developer.recruitmentTask.dao;

import com.developer.recruitmentTask.entity.User;
import com.developer.recruitmentTask.entity.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<User, Long> {

    List<User> findAllByOrderByIdDesc();

    List<User> findByRoleOrderByIdDesc(Role role);
}
