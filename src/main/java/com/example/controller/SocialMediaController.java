package com.example.controller;
import com.example.entity.*;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@Controller
@RequestMapping("")
public class SocialMediaController {
    @Autowired
    public AccountService accountService;
    @Autowired
    public MessageService messageService;
    
    
/**
 * As a user, I should be able to create a new Account on the endpoint
 *  POST localhost:8080/register. The body will contain a representation of a JSON Account,
 *  but will not contain an account_id.
 * The registration will be successful if and only if the username is not blank, the password is at least 4 characters long, 
 * and an Account with that username does not already exist. If all these conditions are met, the response body should contain a JSON of the Account, including its account_id. The response status should be 200 OK, which is the default. The new account should be persisted to the database.
- If the registration is not successful due to a duplicate username, the response status should be 409. (Conflict)
- If the registration is not successful for some other reason, the response status should be 400. (Client error)

 */
@PostMapping("/register")
public @ResponseBody ResponseEntity<Account> registerHandler(@RequestBody Account account){
    if (account.getUsername().trim().equals("") || account.getPassword().length()<4){
        return ResponseEntity.status(400).body(null);
    }
    Account newAccount =accountService.addAccount(account);
    if (newAccount !=null){
        return ResponseEntity.status(200).body(newAccount);
    }else{
        return ResponseEntity.status(409).body(null);
    }
}
    /**
     * I should be able to verify my login on the endpoint POST localhost:8080/login. The request body will contain a JSON representation of an Account.

- The login will be successful if and only if the username and password provided in the request body JSON match a real account existing on the database.
 If successful, the response body should contain a JSON of the account in the response body, including its account_id. The response status should be 200 OK, which is the default.
- If the login is not successful, the response status should be 401. (Unauthorized)
     */
    @PostMapping("/login")
    public @ResponseBody ResponseEntity<Account> postLoginHandler(@RequestBody Account account){
          Account verifiedAccount = accountService.verifiedAccount(account);
          if (verifiedAccount != null){
            return ResponseEntity.status(200).body(verifiedAccount);
          }else{
            return ResponseEntity.status(401).body(null);
          }
    }
    /**
     * As a user, I should be able to submit a new post on the endpoint POST localhost:8080/messages. The request body will contain a JSON representation of a message, which should be persisted to the database, but will not contain a message_id.
    The creation of the message will be successful if and only if the message_text is not blank, is under 255 characters, and posted_by refers to a real, existing user. If successful, the response body should contain a JSON of the message, including its message_id.
 The response status should be 200, which is the default. The new message should be persisted to the database.
If the creation of the message is not successful, the response status should be 400. (Client error)
 */
@PostMapping("/messages")
public @ResponseBody ResponseEntity<Message> postMessageHandler(@RequestBody Message message){
       Message postMessage = messageService.postMessage(message);
       if (postMessage != null){
        return ResponseEntity.status(200).body(postMessage);
       }else{
        return ResponseEntity.status(400).build();
       }
}
/**
 * As a user, I should be able to submit a GET request on the endpoint GET localhost:8080/messages.

- The response body should contain a JSON representation of a list containing all messages retrieved from the database. 
It is expected for the list to simply be empty if there are no messages.
 The response status should always be 200, which is the default.

 */
@GetMapping("/messages")
public @ResponseBody ResponseEntity<List<Message>> getAllMessageHandler(){
        List<Message> listOfMessage = messageService.getMessage();
        return ResponseEntity.status(200).body(listOfMessage);
}
/**
 * As a user, I should be able to submit a GET request on the endpoint GET localhost:8080/messages/{message_id}.

- The response body should contain a JSON representation of the message identified by the message_id
. It is expected for the response body to simply be empty if there is no such message. 
The response status should always be 200, which is the default.
 */
@GetMapping("/messages/{message_id}")
public @ResponseBody ResponseEntity<Message> getMessageByIdHandler(@PathVariable int message_id){
    Message messageById = messageService.getMessageById(message_id);
    if (messageById !=null){
        return ResponseEntity.status(200).body(messageById);
    }
    return ResponseEntity.status(200).build();
}
}