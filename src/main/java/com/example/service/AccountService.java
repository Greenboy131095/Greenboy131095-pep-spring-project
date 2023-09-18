package com.example.service;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.entity.Account;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
   
    
    public Account addAccount(Account account){
        
        List<Account> listOfAccount= accountRepository.findAll();
        for (Account checkAccount : listOfAccount){
            if (checkAccount.getUsername().equals(account.getUsername())){
                return null;
            }
        }
        return (Account)accountRepository.save(account);
    }
    public Account verifiedAccount (Account account){
        List<Account> listOfAccount= accountRepository.findAll();
        for (Account checkedAccount : listOfAccount){
            if (checkedAccount.getUsername().equals(account.getUsername()) && checkedAccount.getPassword().equals(account.getPassword())){
                return checkedAccount;
            }
        }
        return null;

    }
}
