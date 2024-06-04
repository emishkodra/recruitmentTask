package com.developer.recruitmentTask.config.security.repository;

import com.developer.recruitmentTask.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JwtUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
