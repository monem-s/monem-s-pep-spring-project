package com.example.service;

import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.ClientErrorException;

import java.util.Optional;
import java.util.List;


@Service
@Transactional
public class MessageService {

    @Autowired
    private MessageRepository messageRepo;

    @Autowired
    private AccountRepository accountRepo;

    /**
     * Creates a new message.
     * @param message the message to be created.
     * @return new message if text is acceptable and message creation was successful.
     * @throws ClientErrorException if creation failed because of inappropriate text or account not existing.
     */
    public Message createMessage(Message message) {
        // Account check
        if (accountRepo.findAccountByAccountId(message.getPostedBy()).isPresent()) {
            // Text length check
            if (message.getMessageText() != "" && message.getMessageText().length() <= 255) {
                return messageRepo.save(message);
            }
        }

        throw new ClientErrorException();
    }


    /**
     * Updates message.
     * @param id the ID of the message.
     * @param text the new message text.
     * @return updated message if new text is acceptable and update was successful.
     * @throws ClientErrorException if update failed because of inappropriate text or message not existing.
     */
    public Message updateMessage(Integer id, String text) {
        Message toUpdate = getMessageByID(id);

        // New text check
        if (text != "" && text.length() <= 255 && toUpdate != null) {
            toUpdate.setMessageText(text);
            messageRepo.save(toUpdate);
            return toUpdate;
        }

        throw new ClientErrorException();
    }

    /**
     * Deletes message by ID.
     * @param id the ID of the message.
     * @return deleted message if deletion was successful.
     */
    public Long deleteMessage(Integer id) {
        Message toDelete = getMessageByID(id);

        if (toDelete != null) {
            messageRepo.delete(toDelete);
            return Long.valueOf(1); // 1 row deleted
        }

        return null;
    }

    /**
     * Retrieves all messages.
     * @return list with all messages.
     */
    public List<Message> getAllMessages() {
        return messageRepo.findAll();
    }

    /**
     * Retrieves message by ID.
     * @param id the ID of the message.
     * @return message if message ID was found.
     */
    public Message getMessageByID(Integer id) {
        Optional<Message> message = messageRepo.findMessageByMessageId(id);
        if (message.isPresent()) {
            return message.get();
        }

        return null;    
    }

    /**
     * Retrieves all messages from account.
     * @param id the ID of the account.
     * @return messages if account ID was found.
     */
    public List<Message> getMessagesByAccount(Integer id) {
        return messageRepo.findMessagesByPostedBy(id);
    }
}
