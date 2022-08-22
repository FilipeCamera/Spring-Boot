package com.estudo.mvc.service;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.estudo.mvc.models.Message;
import com.estudo.mvc.repository.MessageRepository;

@Service
public class MessageService {
  
  @Autowired
  private MessageRepository messageRepository;

  @Transactional
  public Message save(Message message) {
    return messageRepository.save(message);
  }

  public Page<Message> findAll(Pageable pageable) {
    return messageRepository.findAll(pageable);
  }

  public Optional<Message> findOne(UUID id) {
    return messageRepository.findById(id);
  }
  
  @Transactional
  public void delete(Message message) {
    messageRepository.delete(message);
  }

}
