package com.estudo.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estudo.mvc.models.Role;
import com.estudo.mvc.repository.RoleRepository;

@Service
public class RoleService {

  @Autowired
  private RoleRepository roleRepository;

  public Role getRole(String name) {
    return roleRepository.findByName(name).get();
  }
}
