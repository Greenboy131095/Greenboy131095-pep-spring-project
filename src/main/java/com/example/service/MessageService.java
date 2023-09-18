package com.example.service;
import org.springframework.stereotype.Service;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.entity.*;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MessageService {
    
    MessageRepository messageRepository;
    
    AccountRepository accountRepository;
    @Autowired
    public MessageService(MessageRepository messageRepository,AccountRepository accountRepository){
        this.accountRepository=accountRepository;
        this.messageRepository=messageRepository;
    }
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
    
}
