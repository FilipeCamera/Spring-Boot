package com.estudo.mvc.config;

import java.util.Arrays;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.estudo.mvc.models.Role;
import com.estudo.mvc.models.User;
import com.estudo.mvc.repository.RoleRepository;
import com.estudo.mvc.repository.UserRepository;

@Component
public class DataLoader implements CommandLineRunner{

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public void run(String... args) throws Exception {
    
    Role roleAdmin = new Role("ADMIN");
    Role roleCreator = new Role("CREATOR");
    Role roleUser = new Role("USER");

    if(!roleRepository.findByName("ADMIN").isPresent() 
      && !roleRepository.findByName("USER").isPresent() 
      && !roleRepository.findByName("CREATOR").isPresent()
    ){
      roleRepository.saveAll(Arrays.asList(roleAdmin, roleCreator, roleUser));
    }

    Role role = roleRepository.findByName("ADMIN").get();
    User user = new User();

    user.setFullname("admin");
    user.setNickname("admin");
    user.setEmail("teste-admin@email.com");
    user.setPassword(passwordEncoder.encode("teste-admin"));
    user.setRoles(Arrays.asList(role));

    userRepository.save(user);
  }
  
}
