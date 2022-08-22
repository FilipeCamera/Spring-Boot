package com.estudo.mvc.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estudo.mvc.controllers.dto.MessageDTO;
import com.estudo.mvc.controllers.dto.MessageResponseDTO;
import com.estudo.mvc.models.Message;
import com.estudo.mvc.models.Role;
import com.estudo.mvc.models.User;
import com.estudo.mvc.service.MessageService;
import com.estudo.mvc.service.RoleService;
import com.estudo.mvc.service.UserService;

@RestController
@RequestMapping("/message")
public class MessageController {

  @Autowired
  private MessageService messageService;

  @Autowired
  private UserService userService;

  @Autowired
  private RoleService roleService;
  
  @PostMapping
  @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
  public ResponseEntity<Object> create(@RequestBody @Valid MessageDTO messageDTO, Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    
    Optional<User> existUser = userService.findById(user.getId());

    if(!existUser.isPresent()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{message: user not exists}");
    }

    Role role = roleService.getRole("CREATOR");

    if(role.getAuthority().isEmpty()) {
      return ResponseEntity.internalServerError().body("Error!");
    }

    List<Role> roles = new ArrayList<>();

    roles.add(role);

    Message message = new Message();

    BeanUtils.copyProperties(messageDTO, message);

    message.setUser(user);

    MessageResponseDTO messageResponse = new MessageResponseDTO();

    Message savedMessage = messageService.save(message);

    for(Role roleUser : user.getRoles()) {
        roles.add(roleUser);
    }

    user.setRoles(roles);

    userService.save(user);

    BeanUtils.copyProperties(savedMessage, messageResponse);

    return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
  }

  @GetMapping
  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  public ResponseEntity<Page<Message>> findByAll(@PageableDefault(page = 0,size = 10, sort = "id", direction = Direction.ASC) Pageable pageable) {
    
    return ResponseEntity.ok().body(messageService.findAll(pageable));

  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  public ResponseEntity<Object> findByOne(@PathVariable(value = "id") UUID id) {
    Optional<Message> optinalMessage = messageService.findOne(id);

    if(!optinalMessage.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    MessageResponseDTO message = new MessageResponseDTO();

    BeanUtils.copyProperties(optinalMessage.get(), message);

    return ResponseEntity.ok().body(message);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAnyAuthority('ADMIN', 'CREATOR')")
  public ResponseEntity<Object> update(@RequestBody @Valid MessageDTO messageDTO, @PathVariable(value = "id") UUID id, Authentication authentication) {
    User user = (User) authentication.getPrincipal();

    List<Role> roles = user.getRoles();

    Role role = roles.get(0);

    Optional<Message> messageOptional = messageService.findOne(id);

    if(!messageOptional.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    Message message = messageOptional.get();
    
    if(role.getAuthority().equals("CREATOR")) {
      if(!user.getId().equals(message.getUser().getId())) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não pode editar essa menssagem");
    }

    message.setTitle(messageDTO.getTitle());
    message.setDescription(messageDTO.getDescription());

    messageService.save(message);

    MessageResponseDTO messageUpdated = new MessageResponseDTO();

    BeanUtils.copyProperties(message, messageUpdated);

    return ResponseEntity.ok().body(messageUpdated);
  }


  @DeleteMapping("/{id}")
  @PreAuthorize("hasAnyAuthority('ADMIN', 'CREATOR')")
  public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID id, Authentication authentication) {
    User user = (User) authentication.getPrincipal();

    List<Role> roles = user.getRoles();

    Role role = roles.get(0);

    Optional<Message> messageOptional = messageService.findOne(id);

    if(!messageOptional.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    Message message = messageOptional.get();

    if(role.getAuthority().equals("CREATOR")) {
      if(!user.getId().equals(message.getUser().getId())) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não pode deletar essa menssagem");
    }

    messageService.delete(message);

    return ResponseEntity.ok().body("Deletado com sucesso!");
  }
}
