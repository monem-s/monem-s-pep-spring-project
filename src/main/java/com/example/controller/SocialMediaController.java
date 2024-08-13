package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Message;
import com.example.entity.Account;

import com.example.service.AccountService;
import com.example.service.MessageService;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;


/**
 * DONE: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {
    
    @Autowired
    AccountService accountService;

    @Autowired
    MessageService messageService;


    @PostMapping(value = "/register")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        // Duplicate username check
        Optional<Account> dupCheck = accountService.getAccountByUsername(account.getUsername());
        if (dupCheck.isPresent()) {
            return ResponseEntity.status(409).body(null); // 409
        }

        // Check username and password
        if (account.getUsername().length() < 1 || account.getPassword().length() < 4) {
            return ResponseEntity.status(404).body(null); // 404
        }

        return ResponseEntity.status(200).body(accountService.createAccount(account));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account account) {
        Account toLogin = accountService.loginAccount(account);

        if (toLogin != null) {
            return ResponseEntity.status(200).body(toLogin);
        }

        return ResponseEntity.status(401).body(null); // 401
    }

    @PostMapping(value = "/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        // Account check
        if (accountService.getAccountById(message.getPostedBy()) != null) {
            Message newMessage = messageService.createMessage(message);

            // Check success of message creation
            if (newMessage != null) {
                return ResponseEntity.status(200).body(newMessage);
            }
        }

        return ResponseEntity.status(400).body(null); // 400
    }

    @GetMapping(value = "/messages")
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping(value = "/messages/{messageId}")
    public Message getMessageByID(@PathVariable Integer messageId) {
        return messageService.getMessageByID(messageId);
    }

    @DeleteMapping(value = "/messages/{messageId}")
    public Long deleteMessage(@PathVariable Integer messageId) {
        return messageService.deleteMessage(messageId);
    }

    @PatchMapping(value = "/messages/{messageId}")
    public ResponseEntity<Long> updateMessage(@PathVariable Integer messageId, @RequestBody HashMap<String, String> json) {
        Message updatedMessage = messageService.updateMessage(messageId, json.get("messageText"));

        // Check success
        if (updatedMessage != null && updatedMessage.getMessageText() == json.get("messageText")) {
            return ResponseEntity.status(200).body(Long.valueOf(1));
        }

        return ResponseEntity.status(400).body(null); // 400
    }

    @GetMapping(value = "/accounts/{accountId}/messages")
    public List<Message> getMessagesByAccount(@PathVariable Integer accountId) {
        return messageService.getMessagesByAccount(accountId);
    }


}
