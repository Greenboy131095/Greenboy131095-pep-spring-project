package com.example.service;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.entity.*;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MessageService {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    AccountRepository accountRepository;
    
    public Message postMessage(Message message){
        if (message.getMessage_text().trim().equals("") || message.getMessage_text().length()>254){
            return null;
        }
        List<Account> account = accountRepository.findAll();
        for (Account checkedAcount : account){
            if (checkedAcount.getAccount_id().equals(message.getPosted_by())){
                return messageRepository.save(message);
            }
        }
        
            return null;
        

    }
    public List<Message> getMessage(){
        return messageRepository.findAll();
    }
    public Message getMessageById(int message_id){
          Optional<Message> optionalMessage = messageRepository.findById(message_id);
          if(optionalMessage.isPresent()){
            return optionalMessage.get();
          }else{
            return null;
          }
    }
}
