package com.estudo.mvc.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estudo.mvc.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
  public Optional<Role> findByName(String name);
}
