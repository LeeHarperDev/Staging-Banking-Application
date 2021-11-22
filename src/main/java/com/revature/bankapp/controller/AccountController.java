package com.revature.bankapp.controller;

import com.revature.bankapp.model.Account;
import com.revature.bankapp.model.Transaction;
import com.revature.bankapp.model.User;
import com.revature.bankapp.repository.AccountRepository;
import com.revature.bankapp.repository.UserRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "account")
public class AccountController {

    @Autowired
    @Setter
    private AccountRepository accountRepository;

    @Autowired
    @Setter
    private UserRepository userRepository;

    @PostMapping(path = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> createNewAccount(@RequestBody Account account) {
        User user = this.userRepository.findById(5).get();

        account.setOwner(user);
        Account newAccount = this.accountRepository.save(account);

        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> getAccountInformation(@PathVariable Integer id) {
        Optional<Account> account = this.accountRepository.findById(id);

        if (account.isPresent()) {
            return new ResponseEntity<>(account.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "{id}/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Transaction>> getAccountTransactions(@PathVariable Integer id) {
        Optional<Account> account = this.accountRepository.findById(id);

        if (account.isPresent()) {
            return new ResponseEntity<>(account.get().getTransactions(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "{id}/balance", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> getAccountBalance(@PathVariable Integer id) {
        Optional<Account> account = this.accountRepository.findById(id);

        if (account.isPresent()) {
            return new ResponseEntity<>(account.get().getBalance(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
