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
    public Message patchMessageById(Message message, int message_id){
        if (message.getMessage_text().trim()=="" || message.getMessage_text().length()>254){
            return null;
        }
        Optional<Message> optionalMessage = messageRepository.findById(message_id);
        if (optionalMessage.isPresent()){
            Message updatedMessage = optionalMessage.get();
            updatedMessage.setMessage_text(message.getMessage_text());
            return messageRepository.save(updatedMessage);
        }else{
            return null;
        }
    }
    public List<Message> getMessagesByAccountId(int account_id){
        List<Message> listOfMessage = messageRepository.findAll();
        List<Message> listOfUpdatedMessage = new ArrayList();
        for (Message message : listOfMessage){
            if (message.getPosted_by().equals(account_id)){
                listOfUpdatedMessage.add(message);
            }
        }
        return listOfUpdatedMessage;
    }
    public Message deleteMessageByIdHandler(int message_id){
        Optional<Message> optionalMessage = messageRepository.findById(message_id);
        if (optionalMessage.isPresent()){
            
            Message deletedMessage = optionalMessage.get();

            messageRepository.deleteById(message_id);
            return deletedMessage;
        }else{
            return null;
        }
    }
    
}
