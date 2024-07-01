package com.app.bank.service;

import com.app.bank.entity.Account;
import com.app.bank.repository.AccountRepository;
import org.hibernate.query.criteria.JpaConflictUpdateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static java.lang.Math.pow;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BankInterestService bankInterestService;

    @Autowired
    private RestTemplate restTemplate;

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


    @Value("${bank.interest.rate.endpoint}")
    private String bankInterestRateEndpoint;

    public Double getInterestRate() {
        try {
            ResponseEntity<Double> response = restTemplate.getForEntity(bankInterestRateEndpoint, Double.class);
            return response.getBody();
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
            return null;
        }
    }
//    public double rateOfInterest(){
//        double interestRate = 1; // Example fixed interest rate
//        return interestRate;
//    }


//    @Scheduled(cron = "0 * * * * *")
//    @Scheduled(cron = "0 0 0 1 * ?")
//    public void updateBalances() {
//        List<Account> accounts = accountRepository.findAll(); // Retrieve all accounts
//
//        double rate = bankInterestService.rateOfInterest();
//        int time = 1;
//
//        for (Account account : accounts) {
//            double principal = account.getBalance();
//            double newBalance = principal * (pow((1 + rate / 100), time));
//            account.setBalance(newBalance);
//            accountRepository.save(account);
//        }
//    }
}