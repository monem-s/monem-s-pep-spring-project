package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAll();

    Message findMessageByMessageId(Integer messageId);

    void delete(Message toDelete);

    List<Message> findMessagesByPostedBy(Integer postedBy);

}
