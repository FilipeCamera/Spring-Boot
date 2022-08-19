package com.estudo.mvc.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estudo.mvc.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

  public boolean existsByNickname(String nickname);
  public boolean existsByEmail(String email);
  public Optional<User> findByEmail(String email);
}
