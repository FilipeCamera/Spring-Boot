package com.estudo.mvc.controllers;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estudo.mvc.controllers.dto.UserDTO;
import com.estudo.mvc.controllers.dto.UserResponseDTO;
import com.estudo.mvc.models.User;
import com.estudo.mvc.service.RoleService;
import com.estudo.mvc.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private RoleService roleService;

  @Autowired
  private PasswordEncoder passwordEncoder;
  
  @PostMapping
  public ResponseEntity<Object> create(@RequestBody @Valid UserDTO userDTO) {
    if(userService.existsByEmail(userDTO.getEmail())) {
      return ResponseEntity.badRequest().body("Este usuário já existe");
    }
    if(userService.existsByNickname(userDTO.getNickname())) {
      return ResponseEntity.badRequest().body("Este nick já existe");
    }

    var user = new User();

    userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

    BeanUtils.copyProperties(userDTO, user);

    user.setRoles(Arrays.asList(roleService.getRole("USER")));

    return ResponseEntity.ok().body(userService.save(user));
  }

  @GetMapping
  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  public ResponseEntity<Page<User>> getAllUsers(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
    return ResponseEntity.ok().body(userService.findAll(pageable));
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  public ResponseEntity<Object> getUser(@PathVariable(value = "id") UUID id) {
    Optional<User> optional = userService.findById(id);

    if(!optional.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    UserResponseDTO user = new UserResponseDTO();

    BeanUtils.copyProperties(optional.get(), user);

    return ResponseEntity.ok().body(user);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('ADMIN') or #id == authentication.principal.id")
  public ResponseEntity<Object> update(@RequestBody @Valid UserDTO userDTO, @PathVariable(value = "id") UUID id) {
    Optional<User> optional = userService.findById(id);

    if(!optional.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    User userUpdate = optional.get();

    BeanUtils.copyProperties(userDTO, userUpdate);

    return ResponseEntity.ok().body(userService.save(userUpdate));

  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('ADMIN') or #id == authentication.principal.id")
  public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID id) {
    Optional<User> optional = userService.findById(id);

    if(!optional.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    User user = optional.get();

    userService.remove(user);

    return ResponseEntity.ok().body("Usuário removido!");
  }
}
