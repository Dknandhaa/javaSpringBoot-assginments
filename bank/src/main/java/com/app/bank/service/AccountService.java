package com.app.bank.service;

import com.app.bank.entity.Account;
import com.app.bank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Optional<Account> getAccount(Long id) {
        return accountRepository.findById(id);
    }

    public Account deposit(Long id, double amount) {
        Account account = getAccount(id).orElseThrow(() -> new RuntimeException("Account not found"));
        account.setBalance(account.getBalance() + amount);
        return accountRepository.save(account);
    }

    public Account withdraw(Long id, double amount) {
        Account account = getAccount(id).orElseThrow(() -> new RuntimeException("Account not found"));
        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient funds");
        }
        account.setBalance(account.getBalance() - amount);
        return accountRepository.save(account);
    }

    public double rateOfInterest(){
        double interestRate = 1;
        return interestRate;
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void updateBalances() {
        List<Account> accounts = accountRepository.findAll(); 

        double rate = rateOfInterest();
        int time = 1;

        for (Account account : accounts) {
            double principal = account.getBalance();
            double newBalance = principal * (pow((1 + rate / 100), time));
            account.setBalance(newBalance);
            accountRepository.save(account);
        }
    }


    
}
