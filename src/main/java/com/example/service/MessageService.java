package com.example.service;

import com.example.repository.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;

import java.util.List;

import javax.transaction.Transactional;

@Service
@Transactional
public class MessageService {

    @Autowired
    private MessageRepository messageRepo;

    public Message createMessage(Message message) {
        if (message.getMessageText() != "" && message.getMessageText().length() <= 255) {
            return messageRepo.save(message);
        }

        return null;
    }

    public List<Message> getAllMessages() {
        return messageRepo.findAll();
    }

    public Message getMessageByID(Integer id) {
        return messageRepo.findMessageByMessageId(id);
    }

    public Long deleteMessage(Integer id) {
        Message toDelete = getMessageByID(id);

        if (toDelete != null) {
            messageRepo.delete(toDelete);
            return Long.valueOf(1);
        }

        return null;
    }

    public Message updateMessage(Integer id, String text) {
        Message toUpdate = getMessageByID(id);
        
        if (text.length() > 1 && text.length() <= 255 && toUpdate != null) {
            toUpdate.setMessageText(text);
            messageRepo.save(toUpdate);
            return toUpdate;
        }

        return null;
    }

    public List<Message> getMessagesByAccount(Integer id) {
        return messageRepo.findMessagesByPostedBy(id);
    }
}
