package com.estudo.mvc.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estudo.mvc.models.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {

}
