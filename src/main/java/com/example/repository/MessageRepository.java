package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAll();

    List<Message> findMessagesByPostedBy(Integer postedBy);

    Optional<Message> findMessageByMessageId(Integer messageId);

    void delete(Message toDelete);
}
