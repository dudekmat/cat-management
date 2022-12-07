package com.banzo.catmanagement.auth.repository;

import com.banzo.catmanagement.auth.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);
}
