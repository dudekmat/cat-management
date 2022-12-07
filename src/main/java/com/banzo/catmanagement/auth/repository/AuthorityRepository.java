package com.banzo.catmanagement.auth.repository;

import com.banzo.catmanagement.auth.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {}
