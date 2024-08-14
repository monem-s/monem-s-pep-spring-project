package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import com.example.entity.Message;
import com.example.entity.Account;

import com.example.exception.ClientErrorException;
import com.example.exception.DuplicateUserException;
import com.example.exception.UnauthorizedException;

import com.example.service.AccountService;
import com.example.service.MessageService;

import java.util.HashMap;
import java.util.List;

/**
 * DONE: You will need to write your own endpoints and handlers for your
 * controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use
 * the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations.
 * You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */

@RestController
public class SocialMediaController {

    @Autowired
    AccountService accountService;

    @Autowired
    MessageService messageService;

    // HANDLERS

    /**
     * Handler to register a new account.
     * @param account the account to be registered.
     */
    @PostMapping(value = "/register")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        // Duplicate username check
        //Account dupCheck = accountService.getAccountByUsername(account.getUsername());
        //if (dupCheck != null) {
        //    ResponseEntity.status(409).body(null); // 409
        //}

        // Check username and password
        //if (account.getUsername().length() < 1 || account.getPassword().length() < 4) {
        //    return ResponseEntity.status(404).body(null); // 404
        //}

        return ResponseEntity.status(200).body(accountService.createAccount(account));

    }

    /**
     * Handler to login a account.
     * @param account the account to be logged in.
     */
    @PostMapping(value = "/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account account) {
        Account toLogin = accountService.loginAccount(account);

        //if (toLogin != null) {
        return ResponseEntity.status(200).body(toLogin);
        //}

        //return ResponseEntity.status(401).body(null); // 401
    }

    /**
     * Handler to post a new message.
     * @param message the message to be posted.
     */
    @PostMapping(value = "/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        // Account check
        //if (accountService.getAccountById(message.getPostedBy()) != null) {
        //    
        Message newMessage = messageService.createMessage(message);

        // Check success of message creation
        //    if (newMessage != null) {
        return ResponseEntity.status(200).body(newMessage);
        //    }
        //}

        //return ResponseEntity.status(400).body(null); // 400
    }

    /**
     * Handler to retrieve all messages.
     */
    @GetMapping(value = "/messages")
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    /**
     * Handler to retrieve message by ID.
     * @param messageId the ID of the message to be retrieved.
     */
    @GetMapping(value = "/messages/{messageId}")
    public Message getMessageByID(@PathVariable Integer messageId) {
        return messageService.getMessageByID(messageId);
    }

    /**
     * Handler to delete message by ID.
     * @param messageId the ID of the message to be deleted.
     */
    @DeleteMapping(value = "/messages/{messageId}")
    public Long deleteMessage(@PathVariable Integer messageId) {
        return messageService.deleteMessage(messageId);
    }

    /**
     * Handler to update message by ID.
     * @param messageId the ID of the message to be deleted.
     * @param json the json which contains the new message text.
     */
    @PatchMapping(value = "/messages/{messageId}")
    public ResponseEntity<Long> updateMessage(@PathVariable Integer messageId,
            @RequestBody HashMap<String, String> json) {
        //Message updatedMessage = 
        messageService.updateMessage(messageId, json.get("messageText"));

        // Check success
        //if (updatedMessage != null && updatedMessage.getMessageText() == json.get("messageText")) {
        return ResponseEntity.status(200).body(Long.valueOf(1));
        //}

        //return ResponseEntity.status(400).body(null); // 400
    }

    /**
     * Handler to retrieve all messages from a user.
     * @param messageId the ID of the user from which messages are being retrieved.
     */
    @GetMapping(value = "/accounts/{accountId}/messages")
    public List<Message> getMessagesByAccount(@PathVariable Integer accountId) {
        return messageService.getMessagesByAccount(accountId);
    }

    // EXCEPTION HANDLING

    /**
     * Exception Handler which sends 404 in response to runtime errors.
     * Example taken from lecture "HTTP Status Code and Exception Handling"
     * @param ex the runtime exception.
     */
    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody String handleNotFound(RuntimeException ex) {
        return ex.getMessage();
    }

    /**
     * Exception Handler which sends 409 in response to duplicate user error during registration.
     * @param ex the duplicate user exception.
     */
    @ExceptionHandler({DuplicateUserException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody void handleDuplicateUserSignup(DuplicateUserException ex) {}

    /**
     * Exception Handler which sends 401 in response to unauthorized access error.
     * @param ex the client error.
     */
    @ExceptionHandler({UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public @ResponseBody void handleUnauthorizedError(UnauthorizedException ex) {}

    /**
     * Exception Handler which sends 400 in response to client errors.
     * @param ex the client error.
     */
    @ExceptionHandler({ClientErrorException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody void handleClientError(ClientErrorException ex) {}

}
