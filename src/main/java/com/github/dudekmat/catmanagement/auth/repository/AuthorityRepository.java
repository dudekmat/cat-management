package com.github.dudekmat.catmanagement.auth.repository;

import com.github.dudekmat.catmanagement.auth.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {}
