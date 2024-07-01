package com.app.bank.service;

import com.app.bank.entity.Account;
import com.app.bank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BankInterestService bankInterestService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${bank.interest.rate.endpoint}")
    private String bankInterestRateEndpoint;

    public Double getInterestRate() {
        try {
            ResponseEntity<Double> response = restTemplate.getForEntity(bankInterestRateEndpoint, Double.class);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Optional<Account> getAccount(Long id) {
        return accountRepository.findById(id);
    }

    public Account deposit(Long id, double amount) {
        Account account = getAccount(id).orElseThrow(() -> new RuntimeException("Account not found"));
        Double interestRate = getInterestRate();
        double interestAmount = amount * (1 + interestRate / 100);
        account.setBalance(account.getBalance() + amount + interestAmount);
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

//        Account account = getAccount(id).orElseThrow(() -> new RuntimeException("Account not found"));
//        Double interestRate = getInterestRate();
//
//        if (interestRate != null) {
//            double principal = account.getBalance();
//            int time = 1;
//            double newBalance = principal * Math.pow((1 + interestRate / 100), time);
//            account.setBalance(newBalance);
//            return accountRepository.save(account);
//        } else {
//            throw new RuntimeException("Unable to fetch interest rate");
//        }
//    }
}
