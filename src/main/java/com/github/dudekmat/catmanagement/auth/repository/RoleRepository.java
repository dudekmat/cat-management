package com.github.dudekmat.catmanagement.auth.repository;

import com.github.dudekmat.catmanagement.auth.model.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findByName(String name);
}
