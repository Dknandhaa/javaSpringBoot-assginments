package com.app.bank.controller;

import com.app.bank.service.AccountService;
import com.app.bank.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping
    public Account createAccount(@RequestBody Account account, @RequestHeader(value = "Authorization", required = false) String token) {
        return accountService.createAccount(account);
    }

    @GetMapping("/{id}")
    public Account getAccount(@PathVariable Long id) {
        return accountService.getAccount(id).orElseThrow(() -> new RuntimeException("Account not found"));
    }

    @PostMapping("/{id}/deposit")
    public Account deposit(@PathVariable Long id, @RequestBody Map<String, Double> request,@RequestHeader(value = "Authorization", required = false) String token) {
        Double amount = request.get("amount");
        return accountService.deposit(id, amount);
    }

    @PostMapping("/{id}/withdraw")
    public Account withdraw(@PathVariable Long id, @RequestBody Map<String, Double> request,@RequestHeader(value = "Authorization", required = false) String token) {
        Double amount = request.get("amount");
        return accountService.withdraw(id, amount);
    }

    @GetMapping("/rate")
    public double rateOfInterest(){
        return accountService.rateOfInterest();
    }

    @GetMapping("/{id}/interestAmount")
    public ResponseEntity<String> InterestAmount(@PathVariable Long id){
        double amount;
        amount = (getAccount(id).getBalance()*accountService.rateOfInterest()*1)/100;
        return ResponseEntity.ok("The interest ammount for the time being is " + amount);
    }
}
